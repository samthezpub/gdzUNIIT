package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Exceptions.NoAnswersException;

import java.util.List;

public interface AnswerService {

    void addAnswer(Answer answer);
    List<Answer> findAllAnswersBySubjectId(Long id) throws NoAnswersException;
    List<Answer> findAllAnswersBySubjectNameAndVariant(String name, Short variant) throws NoAnswersException;

    Answer findAnswerByAnswerTitle(String title) throws NoAnswersException;
}
