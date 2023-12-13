package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Exceptions.NoAnswersException;
import com.example.gdzunit.Repositories.AnswerRepository;
import com.example.gdzunit.Services.AnswerService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;


    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }


    @Override
    public void addAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public List<Answer> findAllAnswersBySubjectId(Long id) throws NoAnswersException {
        return answerRepository.findAllAnswersBySubjectId(id).orElseThrow(() -> new NoAnswersException("Нет ответов"));
    }

    @Override
    public List<Answer> findAllAnswersBySubjectNameAndVariant(String name, Short variant) throws NoAnswersException {
        List<Answer> answers = answerRepository.findAllAnswersBySubjectNameAndVariant(name, variant)
                .orElseThrow(() -> new NoAnswersException("Нет ответов с определёнными вариантами!"));

        return answers;
    }

    @Override
    public List<Answer> findAllAnswersBySubjectNameWhichForAllVariants(String name) throws NoAnswersException {
        List<Answer> answers = answerRepository.findAllAnswersBySubjectNameWhichForAllVariants(name)
                .orElseThrow(() -> new NoAnswersException("Нет ответов со всеми вариантами!"));

        return answers;
    }

    public List<Answer> findAllAnswers(String name, Short variant) throws NoAnswersException {
        List<Answer> answers = new LinkedList<>();
        try {
            answers.addAll(findAllAnswersBySubjectNameAndVariant(name, variant)); // список ответов с вариантами
        } catch (NoAnswersException ignored) {}
        try {
            answers.addAll(findAllAnswersBySubjectNameWhichForAllVariants(name)); // список ответов для всех вариантов
        } catch (NoAnswersException ignored) {}

        if (answers.isEmpty()){
            throw new NoAnswersException("Нет ответов совсем!");
        }

        return answers;
    }

    @Override
    public void updateAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public Answer findAnswerByAnswerTitle(String title) throws NoAnswersException {
        return answerRepository.findAnswerByAnswerTitle(title).orElseThrow(() -> new NoAnswersException("Нет ответа"));
    }


}
