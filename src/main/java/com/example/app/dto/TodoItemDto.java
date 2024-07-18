package com.example.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItemDto {
    private String todoBody;
    private boolean hasCompleted;
}
