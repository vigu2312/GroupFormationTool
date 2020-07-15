package com.group14.app.repositories;

import java.util.List;
import com.group14.app.models.Survey;
import com.group14.app.models.SurveyQuestionMapper;

public interface ISurveyRepository {

	Survey getSurveyInfo(String courseId);

	List<SurveyQuestionMapper> getSurveyQuestionsInfo(int surveyId);

	int publishSurvey(int surveyId);
	
	int unpublishSurvey(int surveyId);
	
}
