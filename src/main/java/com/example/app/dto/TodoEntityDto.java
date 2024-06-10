package com.example.app.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TodoEntityDto {
    private String name;
    private List<String> items;
}
