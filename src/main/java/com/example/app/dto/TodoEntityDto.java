package com.example.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoEntityDto {
    private String name;
    private List<TodoItemDto> items;
}
