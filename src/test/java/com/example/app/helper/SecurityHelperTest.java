package com.example.app.helper;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.dto.TodoItemDto;
import com.example.app.entity.TodoEntity;
import com.example.app.entity.TodoItemEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SecurityHelperTest {

    @Test
    void securityCheckTodoEntity() {
        SecurityHelper securityHelper = new SecurityHelper();

        String todoNameThatHasLengthLessThanMax = "s".repeat(GlobalDataHolder.maxTodoNameLength-1);
        String todoNameThatHasLengthEqualsToMax = "s".repeat(GlobalDataHolder.maxTodoNameLength);
        String todoNameThatHasLengthMoreThanMax = "s".repeat(GlobalDataHolder.maxTodoNameLength+1);

        List<TodoItemDto> todoListThatHasLengthLessThanMax = new ArrayList<>();
        List<TodoItemDto> todoListThatHasLengthEqualsToMax = new ArrayList<>();
        List<TodoItemDto> todoListThatHasLengthMoreThanMax = new ArrayList<>();

        for(int l = 0; l < GlobalDataHolder.maxTodoItemCount-1; l++){
            TodoItemDto todoItemDto = new TodoItemDto();
            todoItemDto.setHasCompleted(false);
            todoItemDto.setTodoBody("s".repeat(GlobalDataHolder.maxTodoNameLength-1));

            todoListThatHasLengthLessThanMax.add(todoItemDto);
        }
        for(int l = 0; l < GlobalDataHolder.maxTodoItemCount; l++){
            TodoItemDto todoItemDto = new TodoItemDto();
            todoItemDto.setHasCompleted(false);
            todoItemDto.setTodoBody("s".repeat(GlobalDataHolder.maxTodoNameLength));

            todoListThatHasLengthEqualsToMax.add(todoItemDto);
        }
        for(int l = 0; l < GlobalDataHolder.maxTodoItemCount+1; l++){
            TodoItemDto todoItemDto = new TodoItemDto();
            todoItemDto.setHasCompleted(false);
            todoItemDto.setTodoBody("s".repeat(GlobalDataHolder.maxTodoNameLength+1));

            todoListThatHasLengthMoreThanMax.add(todoItemDto);
        }

        ////////////////////

        TodoEntityDto todoEntityDto1 = new TodoEntityDto();
        todoEntityDto1.setName(todoNameThatHasLengthLessThanMax);
        todoEntityDto1.setItems(todoListThatHasLengthLessThanMax);

        TodoEntityDto todoEntityDto2 = new TodoEntityDto();
        todoEntityDto2.setName(todoNameThatHasLengthEqualsToMax);
        todoEntityDto2.setItems(todoListThatHasLengthEqualsToMax);

        TodoEntityDto todoEntityDto3 = new TodoEntityDto();
        todoEntityDto3.setName(todoNameThatHasLengthMoreThanMax);
        todoEntityDto3.setItems(todoListThatHasLengthMoreThanMax);

        boolean result1_ShouldHaveTrue = securityHelper.securityCheckTodoEntity(todoEntityDto1);
        boolean result2_ShouldHaveTrue = securityHelper.securityCheckTodoEntity(todoEntityDto2);
        boolean result3_ShouldHaveFalse = securityHelper.securityCheckTodoEntity(todoEntityDto3);

        assertTrue(result1_ShouldHaveTrue);
        assertTrue(result2_ShouldHaveTrue);
        assertFalse(result3_ShouldHaveFalse);
    }
}