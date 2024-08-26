package org.example.springexpert.dto.comment.request;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private Long userId;
    private String contents;
}
