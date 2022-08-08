package com.khedmatkar.demo.evaluation.controller;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.evaluation.dto.AnswerDTO;
import com.khedmatkar.demo.evaluation.dto.QuestionDTO;
import com.khedmatkar.demo.evaluation.entity.Answer;
import com.khedmatkar.demo.evaluation.entity.Question;
import com.khedmatkar.demo.evaluation.service.AnswerService;
import com.khedmatkar.demo.evaluation.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RolesAllowed(UserType.Role.USER)
@RequestMapping("/api/evaluation/answers")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/")
    @RolesAllowed(AdminPermission.Role.QUESTIONNAIRE_RW)
    public List<AnswerDTO> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
        return answers.stream()
                .map(AnswerDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/")
    @RolesAllowed(AdminPermission.Role.QUESTIONNAIRE_RW)
    public AnswerDTO getQuestionById(@PathVariable(name = "id") Long id) {
        Answer answer = answerService.getAnswer(id);
        return AnswerDTO.from(answer);
    }
}
