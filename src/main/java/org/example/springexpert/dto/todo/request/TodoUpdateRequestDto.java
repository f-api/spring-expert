package org.example.springexpert.dto.todo.request;

import lombok.Getter;

@Getter
public class TodoUpdateRequestDto {

    private Long userId;
    private String title;
    private String contents;
}
