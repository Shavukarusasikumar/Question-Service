package com.brain.question_service.controller;

import com.brain.question_service.model.Question;
import com.brain.question_service.model.QuestionWrapper;
import com.brain.question_service.model.Response;
import com.brain.question_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;
	private final Environment environment;

	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> allQuestions() {
		return questionService.getAllQuestions();
	}

	@GetMapping("/category/{categoryName}")
	public ResponseEntity<List<Question>> category(@PathVariable String categoryName) {
		return questionService.getQuestionsByCategory(categoryName);
	}

	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}

	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,
														@RequestParam Integer numQuestions) {
		return questionService.getgetQuestionsForQuiz(categoryName, numQuestions);
	}

	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds) {
		return questionService.getQuestionsByIds(questionIds);
	}

	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> response) {
		System.out.println(environment.getProperty("local.server.port"));

		return questionService.getScore(response);
	}
}
