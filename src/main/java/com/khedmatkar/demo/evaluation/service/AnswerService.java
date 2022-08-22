package com.khedmatkar.demo.evaluation.service;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.evaluation.dto.AnswerDTO;
import com.khedmatkar.demo.evaluation.entity.*;
import com.khedmatkar.demo.evaluation.factory.*;
import com.khedmatkar.demo.evaluation.repository.AnswerContentRepository;
import com.khedmatkar.demo.evaluation.repository.AnswerRepository;
import com.khedmatkar.demo.evaluation.repository.QuestionRepository;
import com.khedmatkar.demo.exception.AnswerCreationException;
import com.khedmatkar.demo.exception.AnswerNotMatchException;
import com.khedmatkar.demo.exception.EntityNotFoundException;
import com.khedmatkar.demo.exception.UserNotAllowedException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    public Answer create(AnswerDTO dto, User answerer, ServiceRequest serviceRequest) {
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
                            .answerText(Optional.ofNullable(dto.content.textAnswerDTO)
                                    .orElseThrow(AnswerCreationException::new)
                                    .text)
                            .build());
            case SCORE -> answerContent = QAContentFactory.getAnswerContent(
                    ScoreContentFactory.builder()
                            .questionText(question.getContent().getQuestionText())
                            .maxScore(((ScoreQuestion) question.getContent()).getMaxScore())
                            .minScore(((ScoreQuestion) question.getContent()).getMinScore())
                            .answerScore(Optional.ofNullable(dto.content.scoreAnswerDTO)
                                    .orElseThrow(AnswerCreationException::new)
                                    .score)
                            .build());
            case DOUBLE_CHOICE -> answerContent = QAContentFactory.getAnswerContent(
                    DoubleChoiceContentFactory.builder()
                            .questionText(question.getContent().getQuestionText())
                            .choice1(((DoubleChoiceQuestion) question.getContent()).getChoice1())
                            .choice2(((DoubleChoiceQuestion) question.getContent()).getChoice2())
                            .answerChoice(Optional.ofNullable(dto.content.doubleChoiceAnswerDTO)
                                    .orElseThrow(AnswerCreationException::new)
                                    .answerChoice)
                            .build());
            case MULTIPLE_CHOICE -> answerContent = QAContentFactory.getAnswerContent(
                    MultipleChoiceContentFactory.builder()
                            .questionText(question.getContent().getQuestionText())
                            .choices(((MultipleChoiceQuestion) question.getContent()).getChoices())
                            .isSingleSelection(((MultipleChoiceQuestion) question.getContent()).getIsSingleSelection())
                            .answerChoices(Optional.ofNullable(dto.content.multipleChoiceAnswerDTO)
                                    .orElseThrow(AnswerCreationException::new)
                                    .answerChoices)
                            .build());
        }
        answer.setContent(answerContent);
        answerContentRepository.save(answerContent);
        answerRepository.save(answer);
        return answer;
    }

    @Transactional
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Transactional
    public List<Answer> getAnswersByServiceRequestId(Long id) {
        return answerRepository.findAllByServiceRequestId(id);
    }

    @Transactional
    public Answer getAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}

