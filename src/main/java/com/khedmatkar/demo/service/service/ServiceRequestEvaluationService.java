package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.evaluation.dto.AnswerDTO;
import com.khedmatkar.demo.evaluation.entity.Answer;
import com.khedmatkar.demo.evaluation.entity.Question;
import com.khedmatkar.demo.evaluation.service.AnswerService;
import com.khedmatkar.demo.evaluation.service.QuestionService;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.exception.UserNotAllowedException;
import com.khedmatkar.demo.messaging.entity.ChatStatus;
import com.khedmatkar.demo.messaging.repository.ChatRepository;
import com.khedmatkar.demo.notification.service.AnnouncementMessage;
import com.khedmatkar.demo.notification.service.AnnouncementService;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRequestEvaluationService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AnnouncementService announcementService;
    private final ChatRepository chatRepository;

    public ServiceRequestEvaluationService(
            ServiceRequestRepository serviceRequestRepository,
            QuestionService questionService,
            AnswerService answerService,
            AnnouncementService announcementService,
            ChatRepository chatRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.announcementService = announcementService;
        this.chatRepository = chatRepository;
    }

    @Transactional
    public void finishServiceRequestProgress(Long id, User user) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(ServiceRequestNotFoundException::new);
        if (serviceRequest.getStatus() != ServiceRequestStatus.IN_PROGRESS
                // check if user belongs to the service request //todo refactor
                || !(user.getEmail().equals(serviceRequest.getCustomer().getEmail())
                || user.getEmail().equals(serviceRequest.getAcceptedSpecialist().getEmail()))) {
            throw new UserNotAllowedException();
        }
        serviceRequest.setStatus(ServiceRequestStatus.DONE);
        serviceRequest.getChat().setStatus(ChatStatus.CLOSED);
        chatRepository.save(serviceRequest.getChat());
        serviceRequestRepository.save(serviceRequest);

        // send finish announcement
        if (user.getType() == UserType.ADMIN) {
            announcementService.sendAnnouncementWithEmailToUser(
                    serviceRequest.getAcceptedSpecialist(),
                    AnnouncementMessage.SPECIALIST_FINISHES_SERVICE_REQUEST,
                    serviceRequest.getId()
            );
        }
        announcementService.sendAnnouncementWithEmailToUser(
                serviceRequest.getCustomer(),
                AnnouncementMessage.SPECIALIST_FINISHES_SERVICE_REQUEST,
                serviceRequest.getId()
        );
    }

    @Transactional
    public List<Question> getQuestionnaire(Long id, User user) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(ServiceRequestNotFoundException::new);
        checkAccessToQuestionnaire(serviceRequest, user);
        return questionService.getQuestionnaire(user.getType());
    }

    @Transactional
    public void answerQuestionnaire(Long id, User user, List<AnswerDTO> answersDTO) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(ServiceRequestNotFoundException::new);
        checkAccessToQuestionnaire(serviceRequest, user);
        answersDTO.forEach(answerDTO -> answerService.create(answerDTO, user, serviceRequest));
    }

    public void checkAccessToQuestionnaire(ServiceRequest serviceRequest, User user) {
        List<Answer> answers = answerService.getAnswersByServiceRequestId(serviceRequest.getId());
        int customerQuestionnaireSize = (int) answers.stream()
                .filter(answer -> answer.getQuestion().getAnswererType() == UserType.CUSTOMER).count();
        int specialistQuestionnaireSize = answers.size() - customerQuestionnaireSize;
        if ((serviceRequest.getStatus() != ServiceRequestStatus.DONE)
                // check if user belongs to the service request //todo refactor
                || !(user.getEmail().equals(serviceRequest.getCustomer().getEmail())
                || user.getEmail().equals(serviceRequest.getAcceptedSpecialist().getEmail()))
                // check if user answered questionnaire before
                || ((user.getType() == UserType.CUSTOMER && customerQuestionnaireSize > 0)
                || (user.getType() == UserType.SPECIALIST && specialistQuestionnaireSize > 0))) {
            throw new UserNotAllowedException();
        }
    }
}
