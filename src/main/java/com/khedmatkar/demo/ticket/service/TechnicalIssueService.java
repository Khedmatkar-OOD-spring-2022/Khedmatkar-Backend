package com.khedmatkar.demo.ticket.service;

import com.khedmatkar.demo.account.entity.*;
import com.khedmatkar.demo.exception.EntityNotFoundException;
import com.khedmatkar.demo.exception.TechnicalIssueIsClosedException;
import com.khedmatkar.demo.exception.UserNotAllowedException;
import com.khedmatkar.demo.ticket.dto.TechnicalIssueDTO;
import com.khedmatkar.demo.ticket.dto.TicketDTO;
import com.khedmatkar.demo.ticket.entity.TechnicalIssue;
import com.khedmatkar.demo.ticket.entity.Ticket;
import com.khedmatkar.demo.ticket.entity.TechnicalIssueStatus;
import com.khedmatkar.demo.ticket.repository.TechnicalIssueRepository;
import com.khedmatkar.demo.ticket.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class TechnicalIssueService {

    private final TechnicalIssueRepository technicalIssueRepository;
    private final TicketRepository ticketRepository;


    public TechnicalIssueService(TechnicalIssueRepository technicalIssueRepository,
                                 TicketRepository ticketRepository) {
        this.technicalIssueRepository = technicalIssueRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void create(TechnicalIssueDTO dto, User writer) {
        TechnicalIssue technicalIssue = TechnicalIssue.from(dto, writer);
        technicalIssue.setStatus(TechnicalIssueStatus.OPENED);
        technicalIssue.setAnswers(null);
        technicalIssueRepository.save(technicalIssue);
    }

    @Transactional
    public List<TechnicalIssue> getTechnicalIssues(User user) {
        if (user.getType() == UserType.ADMIN
                && ((Admin) user).has_permission(AdminPermission.TECHNICAL_ISSUE_RW)) {
            return technicalIssueRepository.findAll();
        }
        return technicalIssueRepository.findByWriter(user);
    }

    @Transactional
    public void answerTechnicalIssue(Long id, User answerer, TicketDTO answerDTO) {
        TechnicalIssue technicalIssue = technicalIssueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (answerer.getType() != UserType.ADMIN  && !answerer.getEmail().equals(technicalIssue.getWriter().getEmail())
                || (answerer.getType() == UserType.ADMIN && !((Admin) answerer).has_permission(AdminPermission.TECHNICAL_ISSUE_RW))) {
            throw new UserNotAllowedException();
        }
        if (technicalIssue.getStatus() == TechnicalIssueStatus.CLOSED) {
            throw new TechnicalIssueIsClosedException();
        }
        Ticket answer = Ticket.from(answerDTO, answerer);
        technicalIssue.getAnswers().add(answer);
        ticketRepository.save(answer);
        technicalIssueRepository.save(technicalIssue);
    }

    @Transactional
    public void closeTechnicalIssue(Long id) {
        TechnicalIssue technicalIssue = technicalIssueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        technicalIssue.setStatus(TechnicalIssueStatus.CLOSED);
        technicalIssueRepository.save(technicalIssue);
    }
}

