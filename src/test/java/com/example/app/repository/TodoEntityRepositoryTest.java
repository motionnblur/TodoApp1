package com.example.app.repository;

import com.example.app.entity.TodoEntity;
import com.example.app.entity.TodoItemEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("integration")
class TodoEntityRepositoryTest {
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:15")
            .withDatabaseName("test")
            .withUsername("root")
            .withPassword("root");
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }
    @BeforeAll
    static void beforeAll() {
        postgresqlContainer.start();
    }
    @AfterAll
    static void afterAll() {
        postgresqlContainer.stop();
    }

    @Autowired
    TodoEntityRepository todoEntityRepository;

    @Test
    @Transactional(rollbackFor = Exception.class)
    void shouldSaveTodoIntoDatabase() {
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

        TodoEntity savedTodoEntity = todoEntityRepository.save(todoEntity);
        assertNotNull(savedTodoEntity);
        assertEquals(savedTodoEntity.getTodoName(), "Todo1");
        assertEquals(savedTodoEntity.getTodoItemEntities().get(0).getTodoBody(), "Apple");
        assertEquals(savedTodoEntity.getTodoItemEntities().get(1).getTodoBody(), "Orange");
    }
}