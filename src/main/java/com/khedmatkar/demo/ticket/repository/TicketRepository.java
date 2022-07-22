package com.khedmatkar.demo.ticket.repository;

import com.khedmatkar.demo.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
