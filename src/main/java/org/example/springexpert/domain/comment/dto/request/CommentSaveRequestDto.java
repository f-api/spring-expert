package org.example.springexpert.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    private Long userId;
    private String contents;
}
