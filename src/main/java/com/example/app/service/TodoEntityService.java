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

        todoEntityTemp.setTodoName(todoEntityDto.getName());
        todoEntityTemp.setTodoItems(todoEntityDto.getItems());
        todoEntityTemp.setCompleted(false);

        return todoEntityRepository.save(todoEntityTemp);
    }
    public TodoEntity deleteTodoEntity(String todoEntityName){
        TodoEntity todoEntityToDelete = todoEntityRepository.findByTodoName(todoEntityName);
        todoEntityRepository.delete(todoEntityToDelete);

        return todoEntityToDelete;
    }
    public void updateTodoEntity(TodoEntityDto todoEntityDto){
        TodoEntity todoEntityToUpdate = todoEntityRepository.findByTodoName(todoEntityDto.getName());

        todoEntityToUpdate.setTodoName(todoEntityDto.getName());
        todoEntityToUpdate.setTodoItems(todoEntityDto.getItems());

        todoEntityRepository.save(todoEntityToUpdate);
    }
    public TodoEntity getTodoEntity(String todoEntityName) throws Exception {
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoEntityName);
        if(todoEntity == null) throw new Exception("A todo with that name couldn't found");
        
        return todoEntity;
    }
    public void addTodoItem(String todoName, String item){
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoName);
        todoEntity.getTodoItems().add(item);
        todoEntityRepository.save(todoEntity);
    }
    public void deleteTodoItem(String todoName, String itemToDelete) throws Exception {
        TodoEntity todoEntity = todoEntityRepository.findByTodoName(todoName);
//        String itemToDelete = null;
//        int itemIndis = 0;
//
//        for(String item : todoEntity.getTodoItems()){
//            itemIndis++;
//            if(item.equals(todoItem)){
//                itemToDelete = item;
//                break;
//            }
//        }
//        if(itemToDelete == null)
//            throw new Exception("Todo item couldn't found");

        boolean deleteStatus = todoEntity.getTodoItems().remove(itemToDelete);
        if(!deleteStatus)
            throw new Exception("Todo item couldn't be deleted");

        todoEntityRepository.save(todoEntity);
    }
}
