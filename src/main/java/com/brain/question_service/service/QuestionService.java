package com.brain.question_service.service;

import com.brain.question_service.model.Question;
import com.brain.question_service.model.QuestionWrapper;
import com.brain.question_service.model.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {

	ResponseEntity<List<Question>> getAllQuestions();

	ResponseEntity<List<Question>> getQuestionsByCategory(String name);

	ResponseEntity<String>  addQuestion(Question question);

	ResponseEntity<List<Integer>> getgetQuestionsForQuiz(String categoryName, Integer numQuestions);

	ResponseEntity<List<QuestionWrapper>> getQuestionsByIds(List<Integer> questionIds);

	ResponseEntity<Integer> getScore(List<Response> response);
}
