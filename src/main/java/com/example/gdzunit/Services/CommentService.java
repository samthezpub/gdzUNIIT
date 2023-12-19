package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.Comment;
import com.example.gdzunit.Exceptions.NoCommentsException;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getAllCommentsByAnswerId(Long id) throws NoCommentsException;
}
