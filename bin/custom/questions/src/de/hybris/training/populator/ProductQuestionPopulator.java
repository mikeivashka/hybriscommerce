package de.hybris.training.populator;


import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.training.data.QuestionData;
import de.hybris.training.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.stream.Collectors;

public class ProductQuestionPopulator implements Populator<ProductModel, ProductData> {
    private Converter<QuestionModel, QuestionData> questionConverter;

    @Required
    public void setQuestionConverter(Converter<QuestionModel, QuestionData> questionConverter) {
        this.questionConverter = questionConverter;
    }

    @Override
    public void populate(ProductModel product, ProductData productData) throws ConversionException {
        productData.setQuestionsCount(product.getQuestions().size());
        productData.setQuestions(product.getQuestions().stream()
                .map(questionConverter::convert)
                .collect(Collectors.toList())
        );
    }
}
