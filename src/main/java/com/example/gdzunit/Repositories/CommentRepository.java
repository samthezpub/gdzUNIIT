package com.example.gdzunit.Repositories;

import com.example.gdzunit.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.answer.id = :answer")
    List<Comment> findAllCommentsByAnswerId(@Param("answer") Long answer);

}
