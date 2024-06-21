package com.example.app.filter;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.helper.StringHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestFilterTest {
    @Test
    public void checkIfTodoNameLengthLessThan_Test(){
        StringHelper stringHelper = new StringHelper();

        int expectedMaxLen = GlobalDataHolder.maxTodoNameLength;

        TodoEntityDto todoEntityDto = new TodoEntityDto();
        todoEntityDto.setName("s".repeat(expectedMaxLen));
        int todoNameLenLessThanExpectedMaxLen = todoEntityDto.getName().length();

        boolean result1 = stringHelper.checkIfStringLengthLessThan(expectedMaxLen, todoNameLenLessThanExpectedMaxLen);
        assertTrue(result1);
        /////
        todoEntityDto.setName("s".repeat(expectedMaxLen+1));
        int todoNameLenMoreThanExpectedMaxLen = todoEntityDto.getName().length();

        boolean result2 = stringHelper.checkIfStringLengthLessThan(expectedMaxLen, todoNameLenMoreThanExpectedMaxLen);
        assertFalse(result2);
    }
}