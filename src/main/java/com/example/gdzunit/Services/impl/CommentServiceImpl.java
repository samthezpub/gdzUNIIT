package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Comment;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Repositories.CommentRepository;
import com.example.gdzunit.Services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getAllCommentsByAnswerId(Long id) {
        return commentRepository.findAllCommentsByAnswerId(id);
    }

    @Override
    public void addComment(Comment comment, User user) {
        comment.setPublishTime(LocalDateTime.now());
        comment.setAuthor(user);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

}
