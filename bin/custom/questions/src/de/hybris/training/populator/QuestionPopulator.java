package de.hybris.training.populator;

import de.hybris.platform.commercefacades.user.converters.populator.CustomerPopulator;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.training.data.QuestionData;
import de.hybris.training.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

public class QuestionPopulator implements Populator<QuestionModel, QuestionData> {
    private CustomerPopulator customerPopulator;

    @Override
    public void populate(QuestionModel questionModel, QuestionData questionData) throws ConversionException {
        questionData.setQuestion(questionModel.getQuestion());
        CustomerData customerData = new CustomerData();
        customerPopulator.populate(questionModel.getQuestionAuthor(), customerData);
        questionData.setQuestionAuthor(customerData);
        if (questionModel.getAnswer() != null) {
            questionData.setAnswer(questionModel.getAnswer());
            customerData = new CustomerData();
            customerPopulator.populate(questionModel.getAnswerAuthor(), customerData);
            questionData.setAnswerAuthor(customerData);
        }
    }

    @Required
    public void setCustomerPopulator(CustomerPopulator customerPopulator) {
        this.customerPopulator = customerPopulator;
    }
}
