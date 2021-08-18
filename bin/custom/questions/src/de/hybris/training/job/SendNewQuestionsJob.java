package de.hybris.training.job;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.mail.MailUtils;
import de.hybris.training.model.QuestionModel;
import de.hybris.training.model.SendNewQuestionsCronJobModel;
import de.hybris.training.service.QuestionsService;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SendNewQuestionsJob extends AbstractJobPerformable<SendNewQuestionsCronJobModel> {
    private static final Logger LOG = Logger.getLogger(SendNewQuestionsJob.class);
    private QuestionsService questionsService;
    private ConfigurationService configurationService;

    @Override
    public PerformResult perform(SendNewQuestionsCronJobModel sendNewQuestionsCronJobModel) {
        LOG.info("Sending questions mails. Note that org.apache.commons.mail.send() can block if behind a firewall/proxy.");
        final List<QuestionModel> questions = questionsService.getNewQuestions(sendNewQuestionsCronJobModel.getLastExecutionTime());
        if (questions.isEmpty()) {
            LOG.info("No questions for today, skipping send of ranking mails");
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }
        Map<ProductModel, List<QuestionModel>> groupedQuestions = questions.stream()
                .collect(Collectors.groupingBy(QuestionModel::getProduct));
        final StringBuilder mailContentBuilder = new StringBuilder(2000);
        mailContentBuilder.append("Todays questions summary:\n\n");
        for (final Map.Entry<ProductModel, List<QuestionModel>> entry : groupedQuestions.entrySet()) {
            mailContentBuilder.append("For product: ").append(entry.getKey().getName());
            int index = 1;
            for (QuestionModel question : entry.getValue()) {
                mailContentBuilder.append(index++)
                        .append(". ")
                        .append(question.getQuestion())
                        .append(" by ")
                        .append(question.getQuestionAuthor().getDisplayName())
                        .append("\n")
                        .append(question.getAnswer() != null ?
                                question.getAnswer() + " by " + question.getAnswerAuthor().getDisplayName() :
                                "No answer yet")
                        .append("\n\n");
            }

        }
        try {
            sendEmail(mailContentBuilder.toString());
        } catch (final EmailException e) {
            LOG.error("Problem sending new email. Note that org.apache.commons.mail.send() can block if behind a firewall/proxy.)");
            LOG.error("Problem sending new email.", e);
            return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
        }
        sendNewQuestionsCronJobModel.setLastExecutionTime(new Date());
        modelService.save(sendNewQuestionsCronJobModel);
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private void sendEmail(final String message) throws EmailException {
        final String subject = "Daily Questions Summary";
        // get mail service configuration
        final Email email = MailUtils.getPreConfiguredEmail();
        //send message
        Configuration config = configurationService.getConfiguration();
        String recipient = config.getString("questions_summary_mailing_address", null);
        email.addTo(recipient);
        email.setSubject(subject);
        email.setMsg(message);
        email.setTLS(true);
        email.setSSLOnConnect(true);
        email.send();
        LOG.info(subject);
        LOG.info(message);
    }

    @Required
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Required
    public void setQuestionsService(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }
}
