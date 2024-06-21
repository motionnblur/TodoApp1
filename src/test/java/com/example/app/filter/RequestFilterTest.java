package com.example.app.filter;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestFilterTest {
    @Test
    public void checkIfTodoNameLengthLessThan_Test(){
        int expectedMaxLen = GlobalDataHolder.maxTodoNameLength;

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("s".repeat(expectedMaxLen));

        boolean result1 = checkIfStringLengthLessThan(todoEntityDto, expectedMaxLen);
        assertTrue(result1);
        /////
        todoEntityDto.setName("s".repeat(expectedMaxLen+1));
        boolean result2 = checkIfStringLengthLessThan(todoEntityDto, expectedMaxLen);
        assertFalse(result2);
    }
    private boolean checkIfStringLengthLessThan(TodoEntityDto todoEntityDto, int expectedLength) {
        String name = todoEntityDto.getName();
        return name.length() <= expectedLength;
    }
}