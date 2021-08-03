package de.hybris.training.populator;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;

import static de.hybris.training.constants.QuestionsConstants.QUESTIONS_COUNT;

public class QuestionsCountSearchResultProductPopulator extends SearchResultVariantProductPopulator {
    @Override
    public void populate(SearchResultValueData source, ProductData target) {
        super.populate(source, target);
        final Object obj = this.getValue(source, QUESTIONS_COUNT);
        if (obj != null) {
            target.setQuestionsCount(this.<Integer>getValue(source, QUESTIONS_COUNT));
        } else {
            target.setQuestionsCount(0);
        }
    }
}
