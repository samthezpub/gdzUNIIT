package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.Comment;
import com.example.gdzunit.Entity.User;

import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentsByAnswerId(Long id);
    void addComment(Comment comment, User user);
    void deleteComment(Comment comment);
}
