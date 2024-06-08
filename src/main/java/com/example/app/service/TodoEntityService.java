package com.example.app.service;

import com.example.app.dto.TodoEntityDto;
import com.example.app.dto.TodoEntityUpdateDto;
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
    public TodoEntity deleteTodoEntity(String todoEntityName){
        TodoEntity todoEntityToDelete = todoEntityRepository.findByTodoName(todoEntityName);
        todoEntityRepository.delete(todoEntityToDelete);

        return todoEntityToDelete;
    }
    public void updateTodoEntity(TodoEntityUpdateDto todoEntityUpdateDto){
        TodoEntity todoEntityToUpdate = todoEntityRepository.findByTodoName(todoEntityUpdateDto.getTodoName());

        todoEntityToUpdate.setTodoName(todoEntityUpdateDto.getUpdate().getTodoName());
        todoEntityToUpdate.setTodoBody(todoEntityUpdateDto.getUpdate().getTodoBody());

        todoEntityRepository.save(todoEntityToUpdate);
    }
}
