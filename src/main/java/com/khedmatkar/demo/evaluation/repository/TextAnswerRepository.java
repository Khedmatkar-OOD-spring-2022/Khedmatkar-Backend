package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.TextAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextAnswerRepository extends JpaRepository<TextAnswer, Long> {

}
