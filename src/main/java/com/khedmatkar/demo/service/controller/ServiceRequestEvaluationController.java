package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.evaluation.dto.AnswerDTO;
import com.khedmatkar.demo.evaluation.dto.QuestionDTO;
import com.khedmatkar.demo.evaluation.entity.Question;
import com.khedmatkar.demo.service.service.ServiceRequestEvaluationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/serviceRequests/")
@RolesAllowed(UserType.Role.USER)
public class ServiceRequestEvaluationController {
    private final AccountService accountService;
    private final ServiceRequestEvaluationService serviceRequestEvaluationService;

    public ServiceRequestEvaluationController(AccountService accountService,
                                              ServiceRequestEvaluationService serviceRequestEvaluationService) {
        this.accountService = accountService;
        this.serviceRequestEvaluationService = serviceRequestEvaluationService;
    }

    @PostMapping("/{id}/finish")
    @RolesAllowed({UserType.Role.SPECIALIST, AdminPermission.Role.SERVICE_W})
    public void finishProgress(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                               @PathVariable(name = "id") Long id) {
        User user = accountService.findUserFromUserDetails(userDetails);
        serviceRequestEvaluationService.finishServiceRequestProgress(id, user);
    }

    @GetMapping("/{id}/questionnaire")
    @RolesAllowed({UserType.Role.CUSTOMER, UserType.Role.SPECIALIST})
    @Transactional
    public List<QuestionDTO> getQuestionnaire(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                              @PathVariable(name = "id") Long id) {
        User user = accountService.findUserFromUserDetails(userDetails);
        List<Question> questions = serviceRequestEvaluationService.getQuestionnaire(id, user);
        return questions.stream()
                .map(QuestionDTO::from)
                .collect(Collectors.toList());
    }


    @PostMapping("/{id}/evaluate")
    @RolesAllowed({UserType.Role.CUSTOMER, UserType.Role.SPECIALIST})
    public void answerQuestionnaire(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                    @PathVariable(name = "id") Long id,
                                    @RequestBody @Valid List<AnswerDTO> dtos) {
        User user = accountService.findUserFromUserDetails(userDetails);
        serviceRequestEvaluationService.answerQuestionnaire(id, user, dtos);
    }
}
