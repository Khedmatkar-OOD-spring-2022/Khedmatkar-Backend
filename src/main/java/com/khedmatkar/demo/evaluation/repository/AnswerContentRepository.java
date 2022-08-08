package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.AnswerContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerContentRepository extends JpaRepository<AnswerContent, Long> {

}
