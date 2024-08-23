package org.example.springexpert.controller;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.dto.comment.request.CommentSaveRequestDto;
import org.example.springexpert.dto.comment.request.CommentUpdateRequestDto;
import org.example.springexpert.dto.comment.response.CommentDetailResponseDto;
import org.example.springexpert.dto.comment.response.CommentSaveResponseDto;
import org.example.springexpert.dto.comment.response.CommentUpdateResponseDto;
import org.example.springexpert.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public CommentSaveResponseDto saveComment(
            @PathVariable Long todoId,
            @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return commentService.saveComment(todoId, commentSaveRequestDto);
    }

    @GetMapping("/todos/{todoId}/comments")
    public List<CommentDetailResponseDto> getComments(@PathVariable Long todoId) {
        return commentService.getComments(todoId);
    }

    @GetMapping("/todos/comments/{commentId}")
    public CommentDetailResponseDto getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    @PutMapping("/todos/comments/{commentId}")
    public CommentUpdateResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return commentService.updateComment(commentId, commentUpdateRequestDto);
    }

    @DeleteMapping("/todos/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

}
