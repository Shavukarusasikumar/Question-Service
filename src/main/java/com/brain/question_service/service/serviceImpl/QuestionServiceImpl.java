package com.brain.question_service.service.serviceImpl;

import com.brain.question_service.model.Question;
import com.brain.question_service.model.QuestionWrapper;
import com.brain.question_service.model.Response;
import com.brain.question_service.repository.QuestionRepository;
import com.brain.question_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;

	@Override
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return ResponseEntity.ok(questionRepository.findAll());
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<List<Question>> getQuestionsByCategory(String name) {
		try {
			return ResponseEntity.ok(questionRepository.getQuestionsByCategory(name));
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String>  addQuestion(Question question) {
		try {
			questionRepository.save(question);

			return new ResponseEntity<>("Question added successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<List<Integer>> getgetQuestionsForQuiz(String categoryName, Integer numQuestions) {
		try {
			List<Integer> questions = questionRepository.findRandomQuestionsByCategory(categoryName, numQuestions);

			return new ResponseEntity<>(questions, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<List<QuestionWrapper>> getQuestionsByIds(List<Integer> questionIds) {
		List<QuestionWrapper> questionWrappers = new ArrayList<>();

		for (Integer questionId : questionIds) {
			Optional<Question> optionalQuestion = questionRepository.findById(Long.valueOf(questionId));

			if(optionalQuestion.isPresent()) {
				Question question = optionalQuestion.get();

				QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(), question.getQuestionTitle(),
						question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());

				questionWrappers.add(questionWrapper);
			}
		}

		return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Integer> getScore(List<Response> response) {
		try {
			Integer result = 0;

			for(Response ids : response) {
				Optional<Question> optionalQuestion = questionRepository.findById(ids.getId());

				if(optionalQuestion.isPresent()){
					Question question = optionalQuestion.get();

					if (question.getRightAnswer().equals(ids.getResponse())) {
						result++;
					}
				}
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e){
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
