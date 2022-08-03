package com.khedmatkar.demo.ticket.repository;

import com.khedmatkar.demo.ticket.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
