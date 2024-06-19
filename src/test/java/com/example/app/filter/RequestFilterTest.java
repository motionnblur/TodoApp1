package com.example.app.filter;

import com.example.app.dto.TodoEntityDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestFilterTest {
    @Test
    public void checkIfTodoNameLengthLessThan_Test(){
        int expectedMaxLen = 30;
        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("s".repeat(expectedMaxLen));

        boolean result1 = checkIfTodoNameLengthLessThan(todoEntityDto, expectedMaxLen);
        assertTrue(result1);
        /////
        todoEntityDto.setName("s".repeat(expectedMaxLen+1));
        boolean result2 = checkIfTodoNameLengthLessThan(todoEntityDto, expectedMaxLen);
        assertFalse(result2);
    }
    private boolean checkIfTodoNameLengthLessThan(TodoEntityDto todoEntityDto, int expectedLength) {
        String name = todoEntityDto.getName();
        return name.length() <= expectedLength;
    }
}