package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Subject;
import com.example.gdzunit.Repositories.SubjectRepository;
import com.example.gdzunit.Services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public void addSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public boolean checkIsHave(String subject) {
        return subjectRepository.findAll()
                .stream()
                .filter(subject1 -> subject1.getName().equals(subject))
                .findFirst()
                .isEmpty();
    }

    // Проверить есть ли предмет по названию


}
