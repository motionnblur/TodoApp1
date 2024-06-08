package com.example.app.service;

import com.example.app.dto.TodoEntityDto;
import com.example.app.entity.TodoEntity;
import com.example.app.repository.TodoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoEntityService {
    @Autowired
    protected TodoEntityRepository todoEntityRepository;

    public TodoEntity saveTodoEntity(TodoEntityDto todoEntityDto){
        TodoEntity todoEntityTemp = new TodoEntity();

        todoEntityTemp.setTodoName(todoEntityDto.getTodoName());
        todoEntityTemp.setTodoBody(todoEntityDto.getTodoBody());
        todoEntityTemp.setCompleted(false);

        return todoEntityRepository.save(todoEntityTemp);
    }
}
