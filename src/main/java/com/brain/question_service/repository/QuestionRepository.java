package com.brain.question_service.repository;

import com.brain.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> getQuestionsByCategory(String name);

	@Query(value = "SELECT q.id FROM Question  q WHERE q.category=:category order by Random() LIMIT :numQ",
			nativeQuery = true)
	List<Integer> findRandomQuestionsByCategory(String category, int numQ);
}
