package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.DoubleChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoubleChoiceQuestionRepository extends JpaRepository<DoubleChoiceQuestion, Long> {

}
