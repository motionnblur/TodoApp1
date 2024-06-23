package com.example.app.filter;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.helper.ReaderHelper;
import com.example.app.helper.StringHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
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

    @InjectMocks private RequestFilter filter;
    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private FilterChain chain;
    @Mock private ReaderHelper readerHelper;
    @Mock private StringHelper stringHelper;
    @Mock private PrintWriter printWriter;

    @Test
    public void doFilter_Test() throws ServletException, IOException {
        when(req.getContentType()).thenReturn("application/json");

        String str = "{\"name\": \"sssssssssssssssssssssssssssssss\", \"items\": [\"Elma\", \"Havuç\", \"Armut\"]}";

        when(readerHelper.getStringFromInputStream(any(HttpServletRequest.class))).thenReturn(str);
        when(stringHelper.checkIfStringLengthLessThan(any(Integer.class), any(Integer.class))).thenReturn(false);
        when(res.getWriter()).thenReturn(printWriter);

        filter.doFilter(req, res, chain);

        verify(res).setStatus(HttpStatus.BAD_REQUEST.value());
    }
}