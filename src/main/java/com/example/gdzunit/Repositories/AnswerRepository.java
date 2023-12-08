package com.example.gdzunit.Repositories;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.subject.id = :id")
    Optional<List<Answer>> findAllAnswersBySubjectId(@Param("id") Long id);

    @Query("SELECT a FROM Answer a WHERE a.subject.name = :name")
    Optional<List<Answer>> findAllAnswersBySubjectName(@Param("name") String name);

    @Query("SELECT a FROM Answer a WHERE a.title= :title")
    Optional<Answer> findAnswerByAnswerTitle(@Param("title") String title);
}
