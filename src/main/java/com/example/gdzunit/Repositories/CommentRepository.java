package com.example.gdzunit.Repositories;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.answer.id = :id")
    public Optional<List<Comment>> getCommentByAnswerId(Long id);
}
