package org.example.springexpert.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private Long userId;
    private String contents;
}
