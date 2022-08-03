package com.khedmatkar.demo.ticket.controller;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.ticket.dto.TechnicalIssueDTO;
import com.khedmatkar.demo.ticket.dto.TicketDTO;
import com.khedmatkar.demo.ticket.entity.TechnicalIssue;
import com.khedmatkar.demo.ticket.service.TechnicalIssueService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RolesAllowed(UserType.Role.USER)
@RequestMapping("/api/technical_issues")
public class TechnicalIssueController {
    private final AccountService accountService;
    private final TechnicalIssueService technicalIssueService;

    public TechnicalIssueController(AccountService accountService,
                                    TechnicalIssueService technicalIssueService) {
        this.accountService = accountService;
        this.technicalIssueService = technicalIssueService;
    }

    @PostMapping("/")
    public void sendTechnicalIssue(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                   @RequestBody @Valid TechnicalIssueDTO dto) {
        User user = accountService.findConcreteUserClassFromUserDetails(userDetails);
        technicalIssueService.create(dto, user);
    }

    @GetMapping("/")
    @Transactional
    public List<TechnicalIssueDTO> getAllTechnicalIssues(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        User user = accountService.findConcreteUserClassFromUserDetails(userDetails);
        var technicalIssues = technicalIssueService.getTechnicalIssues(user);
        return technicalIssues.stream()
                .map(TechnicalIssueDTO::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/answer")
    @Transactional
    public void answerTechnicalIssue(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                     @PathVariable(name = "id") Long id,
                                     @RequestBody @Valid TicketDTO dto) {
        User user = accountService.findConcreteUserClassFromUserDetails(userDetails);
        technicalIssueService.answerTechnicalIssue(id, user, dto);
    }

    @PostMapping("/{id}/close")
    @RolesAllowed({AdminPermission.Role.TECHNICAL_ISSUE_RW})
    public void closeTechnicalIssue(@PathVariable(name = "id") Long id) {
        technicalIssueService.closeTechnicalIssue(id);
    }
}
