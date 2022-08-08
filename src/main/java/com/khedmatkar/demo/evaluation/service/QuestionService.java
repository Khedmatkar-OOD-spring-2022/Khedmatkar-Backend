package com.khedmatkar.demo.evaluation.service;

import com.khedmatkar.demo.evaluation.dto.QuestionDTO;
import com.khedmatkar.demo.evaluation.entity.Question;
import com.khedmatkar.demo.evaluation.entity.QuestionContent;
import com.khedmatkar.demo.evaluation.entity.TextQuestion;
import com.khedmatkar.demo.evaluation.factory.QAContentFactory;
import com.khedmatkar.demo.evaluation.factory.TextContentFactory;
import com.khedmatkar.demo.evaluation.repository.QuestionContentRepository;
import com.khedmatkar.demo.evaluation.repository.QuestionRepository;
import com.khedmatkar.demo.evaluation.repository.TextQuestionRepository;
import com.khedmatkar.demo.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionContentRepository questionContentRepository;


    public QuestionService(QuestionRepository questionRepository,
                           QuestionContentRepository questionContentRepository) {
        this.questionRepository = questionRepository;
        this.questionContentRepository = questionContentRepository;
    }

    @Transactional
    public void create(QuestionDTO dto) {
        Question question = Question.from(dto);
        QuestionContent questionContent = null;
        switch (dto.content.contentType) {
            case TEXT -> questionContent = QAContentFactory.getQuestionContent(
                    TextContentFactory.builder()
                            .questionText(dto.content.questionText)
                            .answerWordLength(dto.content.textQuestionDTO.answerWordLength)
                            .build());
            case SCORED, DOUBLE_CHOICE, MULTIPLE_CHOICE -> {
                //todo
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
        questionRepository.deleteById(id);
    }
}

