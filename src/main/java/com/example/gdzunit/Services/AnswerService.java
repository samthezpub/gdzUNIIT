package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Exceptions.NoAnswersException;

import java.util.List;
import java.util.Optional;

public interface AnswerService {

    void addAnswer(Answer answer);
    Answer getAnswerById(Long id) throws NoAnswersException;
    List<Answer> findAllAnswersBySubjectId(Long id) throws NoAnswersException;
    List<Answer> findAllAnswersBySubjectNameAndVariant(String name, Short variant) throws NoAnswersException;
    List<Answer> findAllAnswersBySubjectNameWhichForAllVariants(String name) throws NoAnswersException;
    void updateAnswer(Answer answer);

    Answer findAnswerByAnswerTitle(String title) throws NoAnswersException;
}
