package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Exceptions.NoAnswersException;
import com.example.gdzunit.Repositories.AnswerRepository;
import com.example.gdzunit.Services.AnswerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Answer> findAllAnswersBySubjectId(Long id) throws NoAnswersException {
        return answerRepository.findAllAnswersBySubjectId(id).orElseThrow(() -> new NoAnswersException("Нет ответов"));
    }
}
