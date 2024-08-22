package org.example.springexpert.dto.todo.request;

import lombok.Getter;

@Getter
public class TodoUpdateRequestDto {

    private String username;
    private String title;
    private String contents;
}
