package com.example.app.controller;

import com.example.app.dto.TodoEntityDto;
import com.example.app.entity.TodoEntity;
import com.example.app.repository.TodoEntityRepository;
import com.example.app.service.TodoEntityService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
class TodoEntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoEntityRepository todoEntityRepository;
    @MockBean
    private TodoEntityService todoEntityService;

    @Test
    public void getTodo_ShouldReturn_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo")
                        .param("todoName", "todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addTodo_ShouldReturn_CREATED() throws Exception {
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

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(todoEntity);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/todo")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void changeTodo_ShouldReturn_OK() throws  Exception {
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

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(todoEntity);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/todo")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTodo_ShouldReturn_OK() throws  Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo")
                        .param("todoName", "todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}