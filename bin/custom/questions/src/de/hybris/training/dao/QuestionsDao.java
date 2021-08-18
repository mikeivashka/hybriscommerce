package de.hybris.training.dao;

import de.hybris.training.model.QuestionModel;

import java.util.Date;
import java.util.List;

public interface QuestionsDao {
    List<QuestionModel> findNewQuestions(Date from);
}
