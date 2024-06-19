package com.example.app.filter;

import com.example.app.dto.TodoEntityDto;
import com.example.app.entity.TodoEntity;
import com.example.app.helper.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class RequestFilter implements Filter {
    private final JsonHelper jsonHelper = new JsonHelper();

    @Order(1)
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // The important part!! wrap the request:
        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(req);

        if(!"application/json".equalsIgnoreCase(multiReadHttpServletRequest.getContentType()))
            return;

        StringBuilder requestBodyBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(multiReadHttpServletRequest.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBodyBuilder.append(line);
            }
        } catch (IOException e) {

        }

        ObjectMapper objectMapper = new ObjectMapper();
        TodoEntityDto todoEntityDto = objectMapper.readValue(requestBodyBuilder.toString(), TodoEntityDto.class);

        if (!checkIfTodoNameLengthLessThan(todoEntityDto, 30)) {
            res.setStatus(HttpStatus.BAD_REQUEST.value());
            res.getWriter().write("Todo name length can't be more than "+ 30 +" !");
            return;
        }

        chain.doFilter(multiReadHttpServletRequest, response);
    }

    private boolean checkIfTodoNameLengthLessThan(TodoEntityDto todoEntityDto, int expectedLength) {
        String name = todoEntityDto.getName();
        return name.length() <= expectedLength;
    }
}