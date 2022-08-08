package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.evaluation.entity.QuestionContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionContentRepository extends JpaRepository<QuestionContent, Long> {

}
