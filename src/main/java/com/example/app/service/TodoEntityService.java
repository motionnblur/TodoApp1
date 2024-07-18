package com.example.app.service;

import com.example.app.dto.TodoEntityDto;
import com.example.app.dto.TodoItemDto;
import com.example.app.entity.TodoEntity;
import com.example.app.entity.TodoItemEntity;
import com.example.app.repository.TodoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoEntityService {
    @Autowired
    protected TodoEntityRepository todoEntityRepository;

    public TodoEntity saveTodoEntity(TodoEntityDto todoEntityDto){
        TodoEntity todoEntityTemp = new TodoEntity();
        List<TodoItemEntity> todoItemEntities = new ArrayList<>();

        for(TodoItemDto dto : todoEntityDto.getItems()){
            TodoItemEntity todoItemEntity = new TodoItemEntity();

            todoItemEntity.setTodoBody(dto.getTodoBody());
            todoItemEntity.setCompleted(dto.isHasCompleted());

            todoItemEntities.add(todoItemEntity);
        }

        todoEntityTemp.setTodoName(todoEntityDto.getName());
        todoEntityTemp.setTodoItemEntities(todoItemEntities);

        return todoEntityRepository.save(todoEntityTemp);
    }
    public TodoEntity deleteTodoEntity(String todoEntityName){
        TodoEntity todoEntityToDelete = todoEntityRepository.findByTodoName(todoEntityName);
        todoEntityRepository.delete(todoEntityToDelete);

        return todoEntityToDelete;
    }
    public void updateTodoEntity(TodoEntityDto todoEntityDto) throws Exception {
        TodoEntity todoEntityToUpdate = todoEntityRepository.findByTodoName(todoEntityDto.getName());
        if(todoEntityToUpdate == null) throw new Exception("Todo to delete couldn't be found");

        List<TodoItemEntity> todoItemEntities = new ArrayList<>();

        for(TodoItemDto dto : todoEntityDto.getItems()){
            TodoItemEntity todoItemEntity = new TodoItemEntity();

            todoItemEntity.setTodoBody(dto.getTodoBody());
            todoItemEntity.setCompleted(dto.isHasCompleted());

            todoItemEntities.add(todoItemEntity);
        }

        todoEntityToUpdate.setTodoName(todoEntityDto.getName());
        todoEntityToUpdate.setTodoItemEntities(todoItemEntities);

        todoEntityRepository.save(todoEntityToUpdate);
    }
    public TodoEntity getTodoEntity(String todoEntityName) throws Exception {
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoEntityName);
        if(todoEntity == null) throw new Exception("A todo with that name couldn't be found");
        
        return todoEntity;
    }
    public void addTodoItem(String todoName, String item) throws Exception {
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoName);
        if(todoEntity == null) throw new Exception("A todo with that name couldn't be found");

        TodoItemEntity todoItemEntity = new TodoItemEntity();
        todoItemEntity.setTodoBody(item);
        todoItemEntity.setCompleted(false);

        todoEntity.getTodoItemEntities().add(todoItemEntity);
        todoEntityRepository.save(todoEntity);
    }
    public void deleteTodoItem(String todoName, String itemToDelete) throws Exception {
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoName);
        if(todoEntity == null) throw new Exception("A todo with that name couldn't be found");

        TodoItemEntity entityToBeDeleted = null;

        for(TodoItemEntity e : todoEntity.getTodoItemEntities()){
            if (e.getTodoBody().equals(itemToDelete)) {
                entityToBeDeleted  = e;
                break;
            }
        }

        if(entityToBeDeleted == null)
            throw new Exception("Todo item couldn't be found");

        boolean deleteStatus = todoEntity.getTodoItemEntities().remove(entityToBeDeleted);
        if(!deleteStatus)
            throw new Exception("Todo item couldn't be deleted");

        todoEntityRepository.save(todoEntity);
    }
    public void markTodoItem(String todoName, String todoItemToBeMarked, Boolean markBool) throws Exception {
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoName);
        if(todoEntity == null) throw new Exception("A todo with that name couldn't be found");

        List<TodoItemEntity> todoItemEntities = todoEntity.getTodoItemEntities();

        int index = 0;
        for(TodoItemEntity itemEntity : todoItemEntities){
            String itemBody = itemEntity.getTodoBody();
            if(itemBody.equals(todoItemToBeMarked)){
                todoItemEntities.get(index).setCompleted(markBool);
                todoEntityRepository.save(todoEntity);

                return;
            }

            index++;
        }

        throw new Exception("A todo item with that name couldn't be found");
    }
}
