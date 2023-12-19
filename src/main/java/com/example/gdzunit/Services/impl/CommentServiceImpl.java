package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Comment;
import com.example.gdzunit.Exceptions.NoCommentsException;
import com.example.gdzunit.Repositories.CommentRepository;
import com.example.gdzunit.Services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsByAnswerId(Long id) throws NoCommentsException {
        return commentRepository.getCommentByAnswerId(id).orElseThrow(() -> new NoCommentsException("Комментарии не найдены"));
    }
}
