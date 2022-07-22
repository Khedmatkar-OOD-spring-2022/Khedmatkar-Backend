package com.khedmatkar.demo.ticket.repository;

import com.khedmatkar.demo.ticket.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
