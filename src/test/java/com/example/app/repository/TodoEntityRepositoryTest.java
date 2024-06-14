package com.example.app.repository;

import com.example.app.dto.TodoEntityDto;
import com.example.app.entity.TodoEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
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
        TodoEntity todoEntityTemp = new TodoEntity();

        List<String> todoItems = new ArrayList<>();
        todoItems.add("item1");
        todoItems.add("item2");

        todoEntityTemp.setTodoName("Can");
        todoEntityTemp.setTodoItems(todoItems);
        todoEntityTemp.setCompleted(false);

        TodoEntity savedTodoEntity = todoEntityRepository.save(todoEntityTemp);
        assertNotNull(savedTodoEntity);
        assertEquals(savedTodoEntity.getTodoName(), "Can");
    }
}