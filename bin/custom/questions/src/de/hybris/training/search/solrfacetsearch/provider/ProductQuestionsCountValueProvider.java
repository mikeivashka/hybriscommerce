package de.hybris.training.search.solrfacetsearch.provider;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collection;

public class ProductQuestionsCountValueProvider implements FieldValueProvider {
    private FieldNameProvider fieldNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(IndexConfig indexConfig, IndexedProperty indexedProperty, Object model) throws FieldValueProviderException {
        if (model instanceof ProductModel) {
            final ProductModel product = (ProductModel) model;
            final Collection<FieldValue> fieldValues = new ArrayList<>();
            fieldNameProvider.getFieldNames(indexedProperty, null)
                    .forEach(name -> fieldValues.add(new FieldValue(name, product.getQuestions().size())));
            return fieldValues;
        } else {
            throw new FieldValueProviderException("Provided object is not an instance of ProductModel");
        }
    }

    @Required
    public void setFieldNameProvider(FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }
}
