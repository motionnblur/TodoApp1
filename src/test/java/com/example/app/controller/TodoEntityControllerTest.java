package com.example.app.controller;

import com.example.app.dto.TodoEntityDto;
import com.example.app.dto.TodoItemDto;
import com.example.app.entity.TodoEntity;
import com.example.app.entity.TodoItemEntity;
import com.example.app.filter.RequestFilter;
import com.example.app.helper.ReaderHelper;
import com.example.app.helper.StringHelper;
import com.example.app.repository.TodoEntityRepository;
import com.example.app.service.TodoEntityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
class TodoEntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean private RequestFilter requestFilter;
    @MockBean private HttpServletRequest req;
    @MockBean private HttpServletResponse res;
    @MockBean private FilterChain chain;
    @MockBean private ReaderHelper readerHelper;
    @MockBean private StringHelper stringHelper;
    @MockBean private PrintWriter printWriter;
    @MockBean private ObjectMapper objectMapper;

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
        todoEntity.setTodoItemEntities(todoItemEntities);
        todoEntity.setTodoName("Todo1");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(todoEntity);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/todo")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void changeTodo_ShouldReturn_OK() throws  Exception {
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
        todoEntity.setTodoItemEntities(todoItemEntities);
        todoEntity.setTodoName("Todo1");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(todoEntity);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/todo")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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