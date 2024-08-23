package org.example.springexpert.dto.comment.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDetailResponseDto {

    private final Long id;
    private final String username;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentDetailResponseDto(Long id, String username, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
