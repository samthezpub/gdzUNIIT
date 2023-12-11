package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.Subject;

import java.util.List;

public interface SubjectService {
    void addSubject(Subject subject);
    List<Subject> findAll();

    boolean checkIsHave(String subject);
}
