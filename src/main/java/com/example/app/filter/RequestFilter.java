package com.example.app.filter;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.helper.ReaderHelper;
import com.example.app.helper.StringHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class RequestFilter implements Filter {
    @Autowired ReaderHelper readerHelper;
    @Autowired StringHelper stringHelper;

    ObjectMapper objectMapper = new ObjectMapper();

    @Order(1)
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // The important part!! wrap the request:
        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(req);

        if(!"application/json".equalsIgnoreCase(multiReadHttpServletRequest.getContentType()))
            return;

        String requestBodyAsString = readerHelper.getStringFromInputStream(multiReadHttpServletRequest);
        TodoEntityDto todoEntityDto = objectMapper.readValue(requestBodyAsString, TodoEntityDto.class);

        if (stringHelper.checkIfStringLengthLessThan(GlobalDataHolder.maxTodoNameLength, todoEntityDto.getName().length())) {
            chain.doFilter(multiReadHttpServletRequest, response);
            return;
        }

        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.getWriter().write("Todo name length can't be more than "+ GlobalDataHolder.maxTodoNameLength +" !");
    }


}