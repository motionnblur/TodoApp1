package com.example.app.helper;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.entity.TodoEntity;
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

        List<String> todoListThatHasLengthLessThanMax = new ArrayList<>();
        List<String> todoListThatHasLengthEqualsToMax = new ArrayList<>();
        List<String> todoListThatHasLengthMoreThanMax = new ArrayList<>();

        for(int l = 0; l < GlobalDataHolder.maxTodoItemCount-1; l++){
            todoListThatHasLengthLessThanMax.add("s");
        }
        for(int l = 0; l < GlobalDataHolder.maxTodoItemCount; l++){
            todoListThatHasLengthEqualsToMax.add("s");
        }
        for(int l = 0; l < GlobalDataHolder.maxTodoItemCount+1; l++){
            todoListThatHasLengthMoreThanMax.add("s");
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