package com.khedmatkar.demo.evaluation.controller;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.evaluation.dto.QuestionDTO;
import com.khedmatkar.demo.evaluation.entity.Question;
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
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/")
    @RolesAllowed(AdminPermission.Role.QUESTIONNAIRE_RW)
    public void createQuestion(@RequestBody @Valid QuestionDTO dto) {
        questionService.create(dto);
    }

    @GetMapping("/")
    @RolesAllowed(AdminPermission.Role.QUESTIONNAIRE_RW)
    @Transactional
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return questions.stream()
                .map(QuestionDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/")
    @RolesAllowed(AdminPermission.Role.QUESTIONNAIRE_RW)
    @Transactional
    public QuestionDTO getQuestionById(@PathVariable(name = "id") Long id) {
        Question question = questionService.getQuestion(id);
        return QuestionDTO.from(question);
    }

    @DeleteMapping("/{id}/")
    @RolesAllowed({AdminPermission.Role.TECHNICAL_ISSUE_RW})
    public void removeQuestion(@PathVariable(name = "id") Long id) {
        questionService.deleteQuestion(id);
    }
}
