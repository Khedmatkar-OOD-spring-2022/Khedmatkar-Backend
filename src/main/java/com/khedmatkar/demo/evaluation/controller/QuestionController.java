package com.khedmatkar.demo.evaluation.controller;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.evaluation.dto.QuestionDTO;
import com.khedmatkar.demo.evaluation.service.QuestionService;
import com.khedmatkar.demo.ticket.dto.TechnicalIssueDTO;
import com.khedmatkar.demo.ticket.dto.TicketDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RolesAllowed(UserType.Role.USER)
@RequestMapping("/api/evaluation/questions")
public class QuestionController {
    private final AccountService accountService;
    private final QuestionService questionService;

    public QuestionController(AccountService accountService,
                              QuestionService questionService) {
        this.accountService = accountService;
        this.questionService = questionService;
    }

    @PostMapping("/")
    @RolesAllowed(AdminPermission.Role.QUESTIONNAIRE_RW)
    public void createQuestion(@RequestBody @Valid QuestionDTO dto) {
        questionService.create(dto);
    }

//    @GetMapping("/")
//    @Transactional
//    public List<TechnicalIssueDTO> getAllTechnicalIssues(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
//        User user = accountService.findConcreteUserClassFromUserDetails(userDetails);
//        var technicalIssues = questionService.getTechnicalIssues(user);
//        return technicalIssues.stream()
//                .map(TechnicalIssueDTO::from)
//                .collect(Collectors.toList());
//    }
//
//    @PostMapping("/{id}/answer")
//    @Transactional
//    public void answerTechnicalIssue(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
//                                     @PathVariable(name = "id") Long id,
//                                     @RequestBody @Valid TicketDTO dto) {
//        User user = accountService.findConcreteUserClassFromUserDetails(userDetails);
//        questionService.answerTechnicalIssue(id, user, dto);
//    }
//
//    @PostMapping("/{id}/close")
//    @RolesAllowed({AdminPermission.Role.TECHNICAL_ISSUE_RW})
//    public void closeTechnicalIssue(@PathVariable(name = "id") Long id) {
//        questionService.closeTechnicalIssue(id);
//    }
}
