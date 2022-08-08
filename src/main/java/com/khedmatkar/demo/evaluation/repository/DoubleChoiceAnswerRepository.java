package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.DoubleChoiceAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoubleChoiceAnswerRepository extends JpaRepository<DoubleChoiceAnswer, Long> {

}
