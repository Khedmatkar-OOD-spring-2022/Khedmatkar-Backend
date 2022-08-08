package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
