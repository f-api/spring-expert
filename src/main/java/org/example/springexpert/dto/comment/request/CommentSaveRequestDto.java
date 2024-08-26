package org.example.springexpert.dto.comment.request;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    private Long userId;
    private String contents;
}
