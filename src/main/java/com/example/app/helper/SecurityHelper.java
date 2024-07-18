package com.example.app.helper;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.dto.TodoItemDto;

import java.util.List;

public class SecurityHelper {
    private final StringHelper stringHelper = new StringHelper();

    public boolean securityCheckTodoEntity(TodoEntityDto dto){
        String name = dto.getName();
        List<TodoItemDto> items = dto.getItems();

        if(items.size() > GlobalDataHolder.maxTodoItemCount)
            return false;

        boolean nameCheckRes = stringHelper.checkIfStringLengthLessThan(GlobalDataHolder.maxTodoNameLength, name.length());
        if(!nameCheckRes) return false;

        for(TodoItemDto s : items){
            boolean res = stringHelper.checkIfStringLengthLessThan(GlobalDataHolder.maxTodoNameLength, s.getTodoBody().length());
            if(!res) return false;
        }

        return true;
    }
}
