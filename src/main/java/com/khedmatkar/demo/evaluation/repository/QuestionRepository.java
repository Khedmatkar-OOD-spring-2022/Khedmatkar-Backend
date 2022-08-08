package com.khedmatkar.demo.evaluation.repository;

import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.evaluation.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByAnswererType(UserType AnswererType);
}
