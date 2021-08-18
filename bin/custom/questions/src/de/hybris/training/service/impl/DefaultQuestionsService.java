package de.hybris.training.service.impl;

import de.hybris.training.dao.QuestionsDao;
import de.hybris.training.model.QuestionModel;
import de.hybris.training.service.QuestionsService;
import org.springframework.beans.factory.annotation.Required;

import java.util.Date;
import java.util.List;

public class DefaultQuestionsService implements QuestionsService {

    private QuestionsDao questionsDao;

    @Override
    public List<QuestionModel> getNewQuestions(Date from) {
        return questionsDao.findNewQuestions(from);
    }

    @Required
    public void setQuestionsDao(QuestionsDao questionsDao) {
        this.questionsDao = questionsDao;
    }
}
