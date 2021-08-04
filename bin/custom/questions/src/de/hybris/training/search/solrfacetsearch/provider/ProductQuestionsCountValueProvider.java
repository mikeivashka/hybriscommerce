package de.hybris.training.search.solrfacetsearch.provider;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.RangeNameProvider;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProductQuestionsCountValueProvider implements FieldValueProvider {
    private FieldNameProvider fieldNameProvider;

    private RangeNameProvider rangeNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(IndexConfig indexConfig, IndexedProperty indexedProperty, Object model) {
        List<String> rangeNameList = null;
        int questionsCount = ((ProductModel) model).getQuestions().size();
        try {
            rangeNameList = rangeNameProvider.getRangeNameList(indexedProperty, questionsCount);
        } catch (final FieldValueProviderException e) {
            LOG.error("Could not get Range value", e);
        }
        String rangeName = null;
        if (CollectionUtils.isNotEmpty(rangeNameList)) {
            rangeName = rangeNameList.get(0);
        }
        final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
        final Object valueToPass = (rangeName == null ? questionsCount : rangeName);
        return fieldNames.stream().map(name -> new FieldValue(name, valueToPass)).collect(Collectors.toList());
    }


    @Required
    public void setRangeNameProvider(RangeNameProvider rangeNameProvider) {
        this.rangeNameProvider = rangeNameProvider;
    }

    @Required
    public void setFieldNameProvider(FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }
}
