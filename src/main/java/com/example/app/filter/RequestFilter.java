package com.example.app.filter;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.helper.HttpServletRequestHelper;
import com.example.app.helper.ReaderHelper;
import com.example.app.helper.SecurityHelper;
import com.example.app.helper.StringHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RequestFilter implements Filter {
    @Autowired ReaderHelper readerHelper;

    SecurityHelper securityHelper = new SecurityHelper();
    ObjectMapper objectMapper = new ObjectMapper();

    @Order(1)
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // The important part!! wrap the request:
        HttpServletRequestHelper httpServletRequestHelper = new HttpServletRequestHelper(req);

        if(!"application/json".equalsIgnoreCase(httpServletRequestHelper.getContentType()))
            return;

        String requestBodyAsString = readerHelper.getStringFromInputStream(httpServletRequestHelper);
        TodoEntityDto todoEntityDto = objectMapper.readValue(requestBodyAsString, TodoEntityDto.class);

        if (securityHelper.securityCheckTodoEntity(todoEntityDto)) {
            chain.doFilter(httpServletRequestHelper, response);
            return;
        }

        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.getWriter().write("Todo or item name length can't be more than "+ GlobalDataHolder.maxTodoNameLength +" !");
    }
}