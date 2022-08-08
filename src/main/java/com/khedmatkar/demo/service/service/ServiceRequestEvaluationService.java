package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.evaluation.dto.AnswerDTO;
import com.khedmatkar.demo.evaluation.entity.Question;
import com.khedmatkar.demo.evaluation.repository.QuestionRepository;
import com.khedmatkar.demo.evaluation.service.AnswerService;
import com.khedmatkar.demo.evaluation.service.QuestionService;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.exception.UserNotAllowedException;
import com.khedmatkar.demo.messaging.entity.ChatStatus;
import com.khedmatkar.demo.messaging.repository.ChatRepository;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ServiceRequestEvaluationService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ChatRepository chatRepository;

    public ServiceRequestEvaluationService(
            ServiceRequestRepository serviceRequestRepository,
            QuestionService questionService,
            AnswerService answerService, ChatRepository chatRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.questionService = questionService;
        this.answerService = answerService;
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
        // todo: send notification to customer and specialist
    }

    @Transactional
    public List<Question> getQuestionnaire(Long id, User user) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(ServiceRequestNotFoundException::new);
        if (serviceRequest.getStatus() != ServiceRequestStatus.DONE
                // check if user belongs to the service request //todo refactor
                || !(user.getEmail().equals(serviceRequest.getCustomer().getEmail())
                || user.getEmail().equals(serviceRequest.getAcceptedSpecialist().getEmail()))) {
            throw new UserNotAllowedException();
        }
        return questionService.getQuestionnaire(user.getType());
    }

    @Transactional
    public void answerQuestionnaire(Long id, User user, List<AnswerDTO> answersDTO) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(ServiceRequestNotFoundException::new);
        if ((serviceRequest.getStatus() != ServiceRequestStatus.DONE)
                // check if user belongs to the service request //todo refactor
                || !(user.getEmail().equals(serviceRequest.getCustomer().getEmail())
                || user.getEmail().equals(serviceRequest.getAcceptedSpecialist().getEmail()))
                // check if user answered questionnaire before
                || ((user.getType() == UserType.CUSTOMER && serviceRequest.getCustomerQuestionnaire().size() > 0)
                || (user.getType() == UserType.SPECIALIST && serviceRequest.getSpecialistQuestionnaire().size() > 0))) {
            throw new UserNotAllowedException();
        }
        answersDTO.forEach(answerDTO -> answerService.create(answerDTO, user, serviceRequest));
        // todo: send notification to user
    }
}
