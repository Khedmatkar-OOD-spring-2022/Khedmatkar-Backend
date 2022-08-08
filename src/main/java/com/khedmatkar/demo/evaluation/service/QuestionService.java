package com.khedmatkar.demo.evaluation.service;

import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.evaluation.dto.QuestionDTO;
import com.khedmatkar.demo.evaluation.entity.Question;
import com.khedmatkar.demo.evaluation.entity.QuestionContent;
import com.khedmatkar.demo.evaluation.factory.*;
import com.khedmatkar.demo.evaluation.repository.AnswerRepository;
import com.khedmatkar.demo.evaluation.repository.QuestionContentRepository;
import com.khedmatkar.demo.evaluation.repository.QuestionRepository;
import com.khedmatkar.demo.exception.EntityNotFoundException;
import com.khedmatkar.demo.exception.QuestionCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionContentRepository questionContentRepository;


    public QuestionService(QuestionRepository questionRepository,
                           AnswerRepository answerRepository,
                           QuestionContentRepository questionContentRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.questionContentRepository = questionContentRepository;
    }

    @Transactional
    public void create(QuestionDTO dto) {
        Question question = Question.from(dto);
        QuestionContent questionContent = null;
        switch (dto.content.contentType) {
            case TEXT -> {
                Optional.ofNullable(dto.content.textQuestionDTO).orElseThrow(QuestionCreationException::new);
                questionContent = QAContentFactory.getQuestionContent(
                        TextContentFactory.builder()
                                .questionText(dto.content.questionText)
                                .answerWordLength(dto.content.textQuestionDTO.answerWordLength)
                                .build());
            }
            case SCORE -> {
                Optional.ofNullable(dto.content.scoreQuestionDTO).orElseThrow(QuestionCreationException::new);
                questionContent = QAContentFactory.getQuestionContent(
                        ScoreContentFactory.builder()
                                .questionText(dto.content.questionText)
                                .maxScore(dto.content.scoreQuestionDTO.maxScore)
                                .minScore(dto.content.scoreQuestionDTO.minScore)
                                .build());
            }
            case DOUBLE_CHOICE -> {
                Optional.ofNullable(dto.content.doubleChoiceQuestionDTO).orElseThrow(QuestionCreationException::new);
                questionContent = QAContentFactory.getQuestionContent(
                        DoubleChoiceContentFactory.builder()
                                .questionText(dto.content.questionText)
                                .choice1(dto.content.doubleChoiceQuestionDTO.choice1)
                                .choice2(dto.content.doubleChoiceQuestionDTO.choice2)
                                .build());
            }
            case MULTIPLE_CHOICE -> {
                Optional.ofNullable(dto.content.multipleChoiceQuestionDTO).orElseThrow(QuestionCreationException::new);
                questionContent = QAContentFactory.getQuestionContent(
                        MultipleChoiceContentFactory.builder()
                                .questionText(dto.content.questionText)
                                .choices(dto.content.multipleChoiceQuestionDTO.choices)
                                .isSingleSelection(dto.content.multipleChoiceQuestionDTO.isSingleSelection)
                                .build());
            }
        }
        question.setContent(questionContent);
        questionContentRepository.save(questionContent);
        questionRepository.save(question);
    }

    @Transactional
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Transactional
    public Question getQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        answerRepository.deleteAllByQuestionId(id); //todo implement without cascade
        questionRepository.deleteById(id);
    }

    @Transactional
    public List<Question> getQuestionnaire(UserType answererType) {
        return questionRepository.findAllByAnswererType(answererType);
    }
}

