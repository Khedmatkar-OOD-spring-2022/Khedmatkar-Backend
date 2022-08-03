package com.khedmatkar.demo.ticket.repository;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.ticket.entity.TechnicalIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicalIssueRepository extends JpaRepository<TechnicalIssue, Long> {

    List<TechnicalIssue> findByWriter(User writer);
}
