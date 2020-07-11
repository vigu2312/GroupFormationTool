package com.group14.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.group14.app.models.AllQuestions;
import com.group14.app.models.SurveyAlgorithmInfo;
import com.group14.app.models.SurveyQuestionMapper;
import com.group14.app.models.SurveyRuleMapper;
import com.group14.app.repositories.IGroupFormationAlgorithmRepository;
import com.group14.app.repositories.IQuestionManagerRepository;
import com.group14.app.repositories.ISurveyRepository;

@Service
public class GroupFormationAlgorithmService implements IGroupFormationAlgorithmService {

	private ISurveyRepository surveyRepository;

	private IQuestionManagerRepository questionManagerRepository;

	private IGroupFormationAlgorithmRepository groupFormationAlgorithmRepository;

	public GroupFormationAlgorithmService(ISurveyRepository surveyRepository,
			IQuestionManagerRepository questionManagerRepository,
			IGroupFormationAlgorithmRepository groupFormationAlgorithmRepository) {
		this.surveyRepository = surveyRepository;
		this.questionManagerRepository = questionManagerRepository;
		this.groupFormationAlgorithmRepository = groupFormationAlgorithmRepository;
	}

	@Override
	public List<AllQuestions> getAllSurveyQuestionDetailsById(List<Integer> questionIds) {

		final List<AllQuestions> allQuestionsList = new ArrayList<>();
		questionIds.stream().forEach(qId -> {
			allQuestionsList.add(questionManagerRepository.getQuestionDetailsById(qId + ""));
		});

		return allQuestionsList;
	}

	@Override
	public Map<Integer, Integer> mapQuestionIdWithResponseIdForSurvey(List<SurveyQuestionMapper> surveyQuestions) {
		return surveyQuestions.stream()
				.collect(Collectors.toMap(SurveyQuestionMapper::getQuestionId, SurveyQuestionMapper::getResponseId));
	}

	@Override
	public boolean saveSurveyAlgorithm(SurveyAlgorithmInfo info) {
		boolean isSavePublishInfoSuccess = false;
		if (info.getPublished() == 1) {
			int updatedRows = surveyRepository.publishSurvey(info.getSurveyId());
			if(updatedRows > 0) {
				isSavePublishInfoSuccess = true;
			}
		} else {
			int updatedRows = surveyRepository.unpublishSurvey(info.getSurveyId());
			if(updatedRows > 0) {
				isSavePublishInfoSuccess = true;
			}
		}
		
		List<SurveyRuleMapper> surveyQuestionRules = info.getAlgorithmRules();
		boolean isSaveAlgorithmInfoSuccess = groupFormationAlgorithmRepository.saveAlgorithmRules(surveyQuestionRules);
		return (isSavePublishInfoSuccess && isSaveAlgorithmInfoSuccess);
	}
	
	@Override
	public Map<Integer,SurveyRuleMapper> mapQuestionIdWithSavedAlgorithmRules(List<SurveyQuestionMapper> surveyQuestions){
		final Map<Integer,SurveyRuleMapper> savedRules = new HashMap<>();
		surveyQuestions.stream().forEach(e -> {
			savedRules.put(e.getQuestionId(), this.groupFormationAlgorithmRepository.getSavedAlgorithmRules(e.getResponseId()) );
		});
		return savedRules;
	}

}
