package de.hybris.training.dao.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.training.dao.QuestionsDao;
import de.hybris.training.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DefaultQuestionsDao implements QuestionsDao {
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<QuestionModel> findNewQuestions(Date from) {
        if (from == null) {
            return Collections.emptyList();
        }
        final String queryString =
                "SELECT {p:" + QuestionModel.PK + "} "//
                        + "FROM {" + QuestionModel._TYPECODE + " AS p} "
                        + "WHERE {" + QuestionModel.CREATIONTIME + "} >=  TIMESTAMP(" + from.getTime() + ")";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        return flexibleSearchService.<QuestionModel>search(query).getResult();
    }

    @Required
    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
