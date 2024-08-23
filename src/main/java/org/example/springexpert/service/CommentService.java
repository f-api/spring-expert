package org.example.springexpert.service;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.dto.comment.request.CommentSaveRequestDto;
import org.example.springexpert.dto.comment.request.CommentUpdateRequestDto;
import org.example.springexpert.dto.comment.response.CommentDetailResponseDto;
import org.example.springexpert.dto.comment.response.CommentSaveResponseDto;
import org.example.springexpert.dto.comment.response.CommentUpdateResponseDto;
import org.example.springexpert.entity.Comment;
import org.example.springexpert.entity.Todo;
import org.example.springexpert.repository.CommentRepository;
import org.example.springexpert.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public CommentSaveResponseDto saveComment(Long todoId, CommentSaveRequestDto commentSaveRequestDto) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NullPointerException("Todo not found"));

        Comment comment = new Comment(
                commentSaveRequestDto.getUsername(),
                commentSaveRequestDto.getContents(),
                todo
        );

        Comment savedComment = commentRepository.save(comment);

        return new CommentSaveResponseDto(
                savedComment.getId(),
                savedComment.getUsername(),
                savedComment.getContents(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );

    }


    public List<CommentDetailResponseDto> getComments(Long todoId) {
        List<Comment> commentList = commentRepository.findByTodoId(todoId);

        List<CommentDetailResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentDetailResponseDto dto = new CommentDetailResponseDto(
                    comment.getId(),
                    comment.getUsername(),
                    comment.getContents(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtoList.add(dto);
        }

        return dtoList;
    }

    public CommentDetailResponseDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("Comment not found"));

        return new CommentDetailResponseDto(
                comment.getId(),
                comment.getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(
            Long commentId,
            CommentUpdateRequestDto commentUpdateRequestDto
    ) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("Comment not found"));

        comment.update(
                commentUpdateRequestDto.getContents()
        );

        return new CommentUpdateResponseDto(
                comment.getId(),
                comment.getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
