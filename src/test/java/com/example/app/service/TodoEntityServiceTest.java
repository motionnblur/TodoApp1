package com.example.app.service;

import com.example.app.dto.TodoEntityDto;
import com.example.app.entity.TodoEntity;
import com.example.app.repository.TodoEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item1");
        todoItems.add("item2");

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("todo");
        todoEntityDto.setItems(todoItems);

        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setTodoName(todoEntityDto.getName());
        todoEntity.setTodoItems(todoEntityDto.getItems());
        todoEntity.setCompleted(false);

        when(todoEntityRepository.save(any(TodoEntity.class))).thenReturn(todoEntity);

        TodoEntity todoEntitySaved = todoEntityService.saveTodoEntity(todoEntityDto);

        assertEquals(todoEntitySaved.getTodoName(), todoEntityDto.getName());
        assertEquals(todoEntitySaved.getTodoItems(), todoEntityDto.getItems());
    }

    @Test
    void deleteTodoEntity() throws Exception {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item1");
        todoItems.add("item2");

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("todo");
        todoEntityDto.setItems(todoItems);

        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setTodoName(todoEntityDto.getName());
        todoEntity.setTodoItems(todoEntityDto.getItems());
        todoEntity.setCompleted(false);

        when(todoEntityRepository.findByTodoName(anyString())).thenReturn(todoEntity);
        TodoEntity todoEntityDeleted = todoEntityService.deleteTodoEntity(todoEntity.getTodoName());

        assertEquals(todoEntityDeleted, todoEntity);
    }

    @Test
    void updateTodoEntity() throws Exception {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item1");
        todoItems.add("item2");

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("todo");
        todoEntityDto.setItems(todoItems);

        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setTodoName(todoEntityDto.getName());
        todoEntity.setTodoItems(todoEntityDto.getItems());
        todoEntity.setCompleted(false);

        when(todoEntityRepository.findByTodoName(anyString())).thenReturn(todoEntity);

        List<String> newTodoItems = new ArrayList<>();
        todoItems.add("new item1");
        todoItems.add("new item2");

        TodoEntityDto newTodoEntityDto = new TodoEntityDto();
        newTodoEntityDto.setName("new todo");
        newTodoEntityDto.setItems(newTodoItems);

        todoEntityService.updateTodoEntity(newTodoEntityDto);
        TodoEntity t = todoEntityService.getTodoEntity(newTodoEntityDto.getName());

        assertEquals(t.getTodoName(), newTodoEntityDto.getName());
        assertEquals(t.getTodoItems(), newTodoEntityDto.getItems());
    }

    @Test
    void getTodoEntity() {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item1");
        todoItems.add("item2");

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("todo");
        todoEntityDto.setItems(todoItems);

        TodoEntity todoEntityToBeSaved = new TodoEntity();

        todoEntityToBeSaved.setTodoName(todoEntityDto.getName());
        todoEntityToBeSaved.setTodoItems(todoEntityDto.getItems());
        todoEntityToBeSaved.setCompleted(false);

        when(todoEntityRepository.findByTodoName(anyString())).thenReturn(todoEntityToBeSaved);
        TodoEntity todoEntityToBeGet = todoEntityRepository.findByTodoName(todoEntityToBeSaved.getTodoName());

        assertNotNull(todoEntityToBeGet);
        assertEquals(todoEntityToBeGet, todoEntityToBeSaved);
    }

    @Test
    void addTodoItem() throws Exception {
        List<String> todoItems = new ArrayList<>();
        todoItems.add("item1");
        todoItems.add("item2");

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("todo");
        todoEntityDto.setItems(todoItems);

        TodoEntity todoEntityToBeSaved = new TodoEntity();

        todoEntityToBeSaved.setTodoName(todoEntityDto.getName());
        todoEntityToBeSaved.setTodoItems(todoEntityDto.getItems());
        todoEntityToBeSaved.setCompleted(false);

        when(todoEntityRepository.findByTodoName(anyString())).thenReturn(todoEntityToBeSaved);

        final String itemToBeAdded = "item3";
        todoEntityService.addTodoItem(todoEntityToBeSaved.getTodoName(), itemToBeAdded);

        TodoEntity todoEntity = todoEntityService.getTodoEntity(todoEntityToBeSaved.getTodoName());
        assertEquals(todoEntity.getTodoItems().get(2), itemToBeAdded);
    }
}