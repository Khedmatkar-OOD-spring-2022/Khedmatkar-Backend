package com.khedmatkar.demo.ticket.controller;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.ticket.dto.FeedbackDTO;
import com.khedmatkar.demo.ticket.entity.Feedback;
import com.khedmatkar.demo.ticket.repository.FeedbackRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RolesAllowed(UserType.Role.USER)
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    private final FeedbackRepository feedbackRepository;
    private final AccountService accountService;

    public FeedbackController(FeedbackRepository feedbackRepository,
                              AccountService accountService) {
        this.feedbackRepository = feedbackRepository;
        this.accountService = accountService;
    }

    @PostMapping("/")
    public void sendFeedback(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                             @RequestBody @Valid FeedbackDTO dto) {
        User user = accountService.findUserFromUserDetails(userDetails);
        feedbackRepository.save(
                Feedback.builder()
                        .writer(user)
                        .title(dto.title)
                        .content(dto.content)
                        .build());
    }

    @GetMapping("/")
    @RolesAllowed(AdminPermission.Role.FEEDBACK_RW)
    public List<FeedbackDTO> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream()
                .map(feedback -> FeedbackDTO.builder()
                        .writerEmail(feedback.getWriter().getEmail())
                        .title(feedback.getTitle())
                        .content(feedback.getContent())
                        .timeStamp(feedback.getCreation())
                        .build())
                .collect(Collectors.toList());
    }
}
