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
        todoEntityTemp.setTodoItems(todoEntityDto.getTodoItems());
        todoEntityTemp.setCompleted(false);

        return todoEntityRepository.save(todoEntityTemp);
    }
    public TodoEntity deleteTodoEntity(String todoEntityName){
        TodoEntity todoEntityToDelete = todoEntityRepository.findByTodoName(todoEntityName);
        todoEntityRepository.delete(todoEntityToDelete);

        return todoEntityToDelete;
    }
    public void updateTodoEntity(TodoEntityDto todoEntityDto){
        TodoEntity todoEntityToUpdate = todoEntityRepository.findByTodoName(todoEntityDto.getTodoName());

        todoEntityToUpdate.setTodoName(todoEntityDto.getTodoName());
        todoEntityToUpdate.setTodoItems(todoEntityDto.getTodoItems());

        todoEntityRepository.save(todoEntityToUpdate);
    }
    public TodoEntity getTodoEntity(String todoEntityName) throws Exception {
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoEntityName);
        if(todoEntity == null) throw new Exception("A todo with that name couldn't found");
        
        return todoEntity;
    }
}
