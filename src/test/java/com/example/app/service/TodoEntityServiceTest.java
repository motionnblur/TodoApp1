package com.example.app.service;

import com.example.app.dto.TodoEntityDto;
import com.example.app.dto.TodoItemDto;
import com.example.app.entity.TodoEntity;
import com.example.app.entity.TodoItemEntity;
import com.example.app.repository.TodoEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoEntityServiceTest {
    @Mock
    private TodoEntityRepository todoEntityRepository;
    @InjectMocks
    private TodoEntityService todoEntityService;

    @Test
    void saveTodoEntity() throws Exception {
        TodoItemEntity todoItemEntity1 = new TodoItemEntity();
        todoItemEntity1.setTodoBody("Apple");
        todoItemEntity1.setCompleted(false);

        TodoItemEntity todoItemEntity2 = new TodoItemEntity();
        todoItemEntity2.setTodoBody("Orange");
        todoItemEntity2.setCompleted(false);

        List<TodoItemEntity> todoItemEntities = new ArrayList<>();
        todoItemEntities.add(todoItemEntity1);
        todoItemEntities.add(todoItemEntity2);

        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName("Todo1");
        todoEntity.setTodoItemEntities(todoItemEntities);

        //////////////////////////

        List<TodoItemDto> todoItemDtos = new ArrayList<>();

        TodoItemDto todoItemDto1 = new TodoItemDto();
        todoItemDto1.setTodoBody("Apple");
        todoItemDto1.setHasCompleted(false);

        TodoItemDto todoItemDto2 = new TodoItemDto();
        todoItemDto2.setTodoBody("Orange");
        todoItemDto2.setHasCompleted(true);

        todoItemDtos.add(todoItemDto1);
        todoItemDtos.add(todoItemDto2);

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("Todo1");
        todoEntityDto.setItems(todoItemDtos);

        when(todoEntityRepository.save(any(TodoEntity.class))).thenReturn(todoEntity);

        TodoEntity todoEntitySaved = todoEntityService.saveTodoEntity(todoEntityDto);

        assertEquals(todoEntitySaved.getTodoName(), todoEntityDto.getName());
        assertEquals(todoEntitySaved.getTodoItemEntities().size(), todoEntityDto.getItems().size());
        assertEquals(todoEntitySaved.getTodoItemEntities().get(0).getTodoBody(), todoEntityDto.getItems().get(0).getTodoBody());
        assertEquals(todoEntitySaved.getTodoItemEntities().get(1).getTodoBody(), todoEntityDto.getItems().get(1).getTodoBody());
    }

    @Test
    void deleteTodoEntity() throws Exception {
        TodoItemEntity todoItemEntity1 = new TodoItemEntity();
        todoItemEntity1.setTodoBody("Apple");
        todoItemEntity1.setCompleted(false);

        TodoItemEntity todoItemEntity2 = new TodoItemEntity();
        todoItemEntity2.setTodoBody("Orange");
        todoItemEntity2.setCompleted(false);

        List<TodoItemEntity> todoItemEntities = new ArrayList<>();
        todoItemEntities.add(todoItemEntity1);
        todoItemEntities.add(todoItemEntity2);

        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName("Todo1");
        todoEntity.setTodoItemEntities(todoItemEntities);

        when(todoEntityRepository.findByTodoName(anyString())).thenReturn(todoEntity);
        TodoEntity todoEntityDeleted = todoEntityService.deleteTodoEntity(todoEntity.getTodoName());

        assertEquals(todoEntityDeleted, todoEntity);
    }

    @Test
    void updateTodoEntity() throws Exception {
        TodoItemEntity todoItemEntity1 = new TodoItemEntity();
        todoItemEntity1.setTodoBody("Apple");
        todoItemEntity1.setCompleted(false);

        TodoItemEntity todoItemEntity2 = new TodoItemEntity();
        todoItemEntity2.setTodoBody("Orange");
        todoItemEntity2.setCompleted(false);

        List<TodoItemEntity> todoItemEntities = new ArrayList<>();
        todoItemEntities.add(todoItemEntity1);
        todoItemEntities.add(todoItemEntity2);

        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodoName("Todo1");
        todoEntity.setTodoItemEntities(todoItemEntities);

        //////////////////////////

        List<TodoItemDto> todoItemDtos = new ArrayList<>();

        TodoItemDto todoItemDto1 = new TodoItemDto();
        todoItemDto1.setTodoBody("Apple");
        todoItemDto1.setHasCompleted(false);

        TodoItemDto todoItemDto2 = new TodoItemDto();
        todoItemDto2.setTodoBody("Orange");
        todoItemDto2.setHasCompleted(true);

        todoItemDtos.add(todoItemDto1);
        todoItemDtos.add(todoItemDto2);

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("Todo1");
        todoEntityDto.setItems(todoItemDtos);

        when(todoEntityRepository.findByTodoName(anyString())).thenReturn(todoEntity);

        todoEntityService.updateTodoEntity(todoEntityDto);
        TodoEntity t = todoEntityService.getTodoEntity(todoEntityDto.getName());

        assertEquals(t.getTodoName(), todoEntityDto.getName());
        assertEquals(t.getTodoItemEntities().size(), todoEntityDto.getItems().size());
        assertEquals(t.getTodoItemEntities().get(0).getTodoBody(), todoEntityDto.getItems().get(0).getTodoBody());
        assertEquals(t.getTodoItemEntities().get(1).getTodoBody(), todoEntityDto.getItems().get(1).getTodoBody());
    }

    @Test
    void getTodoEntity() {
        TodoItemEntity todoItemEntity1 = new TodoItemEntity();
        todoItemEntity1.setTodoBody("Apple");
        todoItemEntity1.setCompleted(false);

        TodoItemEntity todoItemEntity2 = new TodoItemEntity();
        todoItemEntity2.setTodoBody("Orange");
        todoItemEntity2.setCompleted(false);

        List<TodoItemEntity> todoItemEntities = new ArrayList<>();
        todoItemEntities.add(todoItemEntity1);
        todoItemEntities.add(todoItemEntity2);

        TodoEntity todoEntityToBeSaved = new TodoEntity();
        todoEntityToBeSaved.setTodoName("Todo1");
        todoEntityToBeSaved.setTodoItemEntities(todoItemEntities);

        when(todoEntityRepository.findByTodoName(anyString())).thenReturn(todoEntityToBeSaved);
        TodoEntity todoEntityToBeGet = todoEntityRepository.findByTodoName(todoEntityToBeSaved.getTodoName());

        assertNotNull(todoEntityToBeGet);
        assertEquals(todoEntityToBeGet, todoEntityToBeSaved);
    }

    @Test
    void addTodoItem() throws Exception {
        TodoItemEntity todoItemEntity1 = new TodoItemEntity();
        todoItemEntity1.setTodoBody("Apple");
        todoItemEntity1.setCompleted(false);

        TodoItemEntity todoItemEntity2 = new TodoItemEntity();
        todoItemEntity2.setTodoBody("Orange");
        todoItemEntity2.setCompleted(false);

        List<TodoItemEntity> todoItemEntities = new ArrayList<>();
        todoItemEntities.add(todoItemEntity1);
        todoItemEntities.add(todoItemEntity2);

        TodoEntity todoEntityToBeSaved = new TodoEntity();
        todoEntityToBeSaved.setTodoName("Todo1");
        todoEntityToBeSaved.setTodoItemEntities(todoItemEntities);
        when(todoEntityRepository.findByTodoName(anyString())).thenReturn(todoEntityToBeSaved);

        final String itemToBeAdded = "Pen";
        todoEntityService.addTodoItem(todoEntityToBeSaved.getTodoName(), itemToBeAdded);

        TodoEntity todoEntity = todoEntityService.getTodoEntity(todoEntityToBeSaved.getTodoName());
        assertEquals(todoEntity.getTodoItemEntities().get(2).getTodoBody(), itemToBeAdded);
    }
}