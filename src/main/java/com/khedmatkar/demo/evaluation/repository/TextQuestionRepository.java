package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.TextQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextQuestionRepository extends JpaRepository<TextQuestion, Long> {

}
