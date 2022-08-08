package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.ScoreAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreAnswerRepository extends JpaRepository<ScoreAnswer, Long> {

}
