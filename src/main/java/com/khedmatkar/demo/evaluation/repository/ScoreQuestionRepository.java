package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.ScoreQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreQuestionRepository extends JpaRepository<ScoreQuestion, Long> {

}
