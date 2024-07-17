package com.example.app.filter;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.helper.HttpServletRequestHelper;
import com.example.app.helper.ReaderHelper;
import com.example.app.helper.SecurityHelper;
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

    @Mock private HttpServletRequest mockRequest;
    @Mock private HttpServletResponse mockResponse;
    @Mock private FilterChain mockFilterChain;
    @Mock private ReaderHelper readerHelper;
    @Mock private SecurityHelper securityHelper;
    @Mock private HttpServletRequestHelper httpServletRequestHelper;
    @Mock private PrintWriter printWriter;
    @InjectMocks private RequestFilter requestFilter;

    @Test
    public void testDoFilter_PutRequest_ValidJson_SecurityCheckPasses() throws IOException, ServletException {
        // Mock request data
        String requestUrl = "http://localhost:8080/todo";
        String requestMethod = "PUT";
        String validJson = "{\"name\": \"Todo1\",\"items\": [{\"todoBody\":\"Apple\",\"hasCompleted\": false},{\"todoBody\":\"Appleee\",\"hasCompleted\": true}]}";
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer(requestUrl));
        when(mockRequest.getMethod()).thenReturn(requestMethod);
        when(mockRequest.getContentType()).thenReturn("application/json");

        // Mock reading request body
        when(readerHelper.getStringFromInputStream(any(HttpServletRequestHelper.class))).thenReturn(validJson);
        when(securityHelper.securityCheckTodoEntity(any(TodoEntityDto.class))).thenReturn(true);

        // Execute the filter
        requestFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        // Verify chain is called
        verify(mockFilterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
        verify(mockResponse, never()).setStatus(anyInt()); // No error status set
    }

    @Test
    public void testDoFilter_PostRequest_ShouldReturn() throws IOException, ServletException{
        String validJson = "{\"name\": \"Todo1\",\"items\": [{\"todoBody\":\"Apple\",\"hasCompleted\": false},{\"todoBody\":\"Appleee\",\"hasCompleted\": true}]}";

        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/todo"));
        when(mockRequest.getMethod()).thenReturn("POST");

        when(readerHelper.getStringFromInputStream(any(HttpServletRequestHelper.class))).thenReturn(validJson);
        when(securityHelper.securityCheckTodoEntity(any(TodoEntityDto.class))).thenReturn(true);

        requestFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        verify(mockFilterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
        verify(mockResponse, never()).setStatus(anyInt());
    }

    @Test
    public void testDoFilter_GetRequest_ShouldReturn() throws IOException, ServletException{
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/todo"));
        when(mockRequest.getMethod()).thenReturn("GET");

        String validTodoName = "s".repeat(GlobalDataHolder.maxTodoNameLength - 1);
        when(mockRequest.getParameter("todoName")).thenReturn(validTodoName);

        requestFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        verify(mockFilterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
        verify(mockResponse, never()).setStatus(anyInt()); // No error status set
    }

    @Test
    public void testDoFilter_GetRequest_ShouldNotReturn() throws IOException, ServletException{
        when(mockResponse.getWriter()).thenReturn(printWriter);

        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/todo"));
        when(mockRequest.getMethod()).thenReturn("GET");

        String validTodoName = "s".repeat(GlobalDataHolder.maxTodoNameLength + 1);
        when(mockRequest.getParameter("todoName")).thenReturn(validTodoName);

        requestFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        verify(mockResponse).setStatus(HttpStatus.BAD_REQUEST.value()); // No error status set
    }

    @Test
    public void testDoFilter_DeleteRequest_ShouldReturn() throws IOException, ServletException{
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/todo"));
        when(mockRequest.getMethod()).thenReturn("DELETE");

        String validTodoName = "s".repeat(GlobalDataHolder.maxTodoNameLength - 1);
        when(mockRequest.getParameter("todoName")).thenReturn(validTodoName);

        requestFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        verify(mockFilterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
        verify(mockResponse, never()).setStatus(anyInt()); // No error status set
    }

    @Test
    public void testDoFilter_DeleteRequest_ShouldNotReturn() throws IOException, ServletException{
        when(mockResponse.getWriter()).thenReturn(printWriter);

        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/todo"));
        when(mockRequest.getMethod()).thenReturn("DELETE");

        String validTodoName = "s".repeat(GlobalDataHolder.maxTodoNameLength + 1);
        when(mockRequest.getParameter("todoName")).thenReturn(validTodoName);

        requestFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        verify(mockResponse).setStatus(HttpStatus.BAD_REQUEST.value()); // No error status set
    }

    @Test
    public void testDoFilter_ShouldReturn_BadRequest() throws IOException, ServletException{
        when(mockResponse.getWriter()).thenReturn(printWriter);

        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/todo"));
        when(mockRequest.getMethod()).thenReturn("ANY");

        requestFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        verify(mockResponse).setStatus(HttpStatus.BAD_REQUEST.value());
    }
}