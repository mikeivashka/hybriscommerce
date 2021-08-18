package de.hybris.training.service;

import de.hybris.training.model.QuestionModel;

import java.util.Date;
import java.util.List;

public interface QuestionsService {
    List<QuestionModel> getNewQuestions(Date from);
}
