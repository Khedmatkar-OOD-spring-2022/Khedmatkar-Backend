package com.khedmatkar.demo.evaluation.service;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.evaluation.dto.AnswerDTO;
import com.khedmatkar.demo.evaluation.entity.Answer;
import com.khedmatkar.demo.evaluation.entity.AnswerContent;
import com.khedmatkar.demo.evaluation.entity.Question;
import com.khedmatkar.demo.evaluation.entity.TextQuestion;
import com.khedmatkar.demo.evaluation.factory.QAContentFactory;
import com.khedmatkar.demo.evaluation.factory.TextContentFactory;
import com.khedmatkar.demo.evaluation.repository.AnswerContentRepository;
import com.khedmatkar.demo.evaluation.repository.AnswerRepository;
import com.khedmatkar.demo.evaluation.repository.QuestionRepository;
import com.khedmatkar.demo.exception.AnswerNotMatchException;
import com.khedmatkar.demo.exception.EntityNotFoundException;
import com.khedmatkar.demo.exception.UserNotAllowedException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerContentRepository answerContentRepository;
    private final QuestionRepository questionRepository;


    public AnswerService(AnswerRepository answerRepository,
                         AnswerContentRepository answerContentRepository,
                         QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.answerContentRepository = answerContentRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void create(AnswerDTO dto, User answerer, ServiceRequest serviceRequest) {
        Question question = questionRepository.findById(dto.questionId)
                .orElseThrow(EntityNotFoundException::new);
        if (answerer.getType() != question.getAnswererType()) {
            throw new UserNotAllowedException();
        }
        if (question.getContent().getContentType() != dto.content.contentType) {
            throw new AnswerNotMatchException();
        }
        Answer answer = Answer.builder()
                .question(question)
                .answerer(answerer)
                .serviceRequest(serviceRequest)
                .build();
        AnswerContent answerContent = null;
        switch (dto.content.contentType) {
            case TEXT -> answerContent = QAContentFactory.getAnswerContent(
                    TextContentFactory.builder()
                            .questionText(question.getContent().getQuestionText())
                            .answerWordLength(((TextQuestion) question.getContent()).getAnswerWordLength())
                            .answerText(dto.content.textAnswerDTO.text)
                            .build());
            case SCORED, DOUBLE_CHOICE, MULTIPLE_CHOICE -> {
                //todo
            }
        }
        answer.setContent(answerContent);
        answerContentRepository.save(answerContent);
        answerRepository.save(answer);
    }

    @Transactional
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Transactional
    public Answer getAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}

