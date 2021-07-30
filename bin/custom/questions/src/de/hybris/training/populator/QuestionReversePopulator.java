package de.hybris.training.populator;

import de.hybris.platform.commercefacades.user.converters.populator.CustomerReversePopulator;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.training.data.QuestionData;
import de.hybris.training.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

public class QuestionReversePopulator implements Populator<QuestionData, QuestionModel> {
    private CustomerReversePopulator customerPopulator;

    @Override
    public void populate(QuestionData questionData, QuestionModel questionModel) throws ConversionException {
        questionModel.setQuestion(questionData.getQuestion());
        questionModel.setAnswer(questionData.getAnswer());
        CustomerModel customerModel = new CustomerModel();
        customerPopulator.populate(questionData.getQuestionAuthor(), customerModel);
        questionModel.setQuestionAuthor(customerModel);
        customerModel = new CustomerModel();
        customerPopulator.populate(questionData.getAnswerAuthor(), customerModel);
        questionModel.setAnswerAuthor(customerModel);
    }

    @Required
    public void setCustomerPopulator(CustomerReversePopulator customerPopulator) {
        this.customerPopulator = customerPopulator;
    }
}
