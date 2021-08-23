package de.hybris.training.populator;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;

import static de.hybris.training.constants.QuestionsConstants.WARRANTY_YEARS;

public class ProductWarrantyYearsSearchResultPopulator extends SearchResultVariantProductPopulator {
    @Override
    public void populate(SearchResultValueData source, ProductData target) {
        super.populate(source, target);
        final Object obj = this.getValue(source, WARRANTY_YEARS);
        if (obj != null) {
            target.setWarrantyYears(this.getValue(source, WARRANTY_YEARS));
        } else {
            target.setWarrantyYears(String.valueOf(0));
        }
    }
}
