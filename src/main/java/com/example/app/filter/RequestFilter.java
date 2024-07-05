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

        String requestUrl = req.getRequestURL().toString();

        if(req.getMethod().equals("POST")){
            if(requestUrl.equals("http://localhost:8080/todo")){
                String requestBodyAsString = readerHelper.getStringFromInputStream(httpServletRequestHelper);
                TodoEntityDto todoEntityDto = objectMapper.readValue(requestBodyAsString, TodoEntityDto.class);

                if (securityHelper.securityCheckTodoEntity(todoEntityDto)) {
                    chain.doFilter(httpServletRequestHelper, response);
                    return;
                }

                res.setStatus(HttpStatus.BAD_REQUEST.value());
                res.getWriter().write("Todo or item name length can't be more than "+ GlobalDataHolder.maxTodoNameLength +" !");
            }else if(requestUrl.equals("http://localhost:8080/todo/addItem")){
                chain.doFilter(httpServletRequestHelper, response);
                return;
            }
        }else if(req.getMethod().equals("PUT")){
            if(!"application/json".equalsIgnoreCase(httpServletRequestHelper.getContentType())){
                res.setStatus(HttpStatus.BAD_REQUEST.value());
                res.getWriter().write("should have application/json");
                return;
            }

            String requestBodyAsString = readerHelper.getStringFromInputStream(httpServletRequestHelper);
            TodoEntityDto todoEntityDto = objectMapper.readValue(requestBodyAsString, TodoEntityDto.class);

            if (securityHelper.securityCheckTodoEntity(todoEntityDto)) {
                chain.doFilter(httpServletRequestHelper, response);
                return;
            }

            res.setStatus(HttpStatus.BAD_REQUEST.value());
            res.getWriter().write("Todo or item name length can't be more than "+ GlobalDataHolder.maxTodoNameLength +" !");
        }else if(req.getMethod().equals("GET") || req.getMethod().equals("DELETE")){
            chain.doFilter(httpServletRequestHelper, response);
            return;
        }

        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.getWriter().write("Bad request");

//        String requestBodyAsString = readerHelper.getStringFromInputStream(httpServletRequestHelper);
//        TodoEntityDto todoEntityDto = objectMapper.readValue(requestBodyAsString, TodoEntityDto.class);
//
//        if (securityHelper.securityCheckTodoEntity(todoEntityDto)) {
//            chain.doFilter(httpServletRequestHelper, response);
//            return;
//        }
//
//        res.setStatus(HttpStatus.BAD_REQUEST.value());
//        res.getWriter().write("Todo or item name length can't be more than "+ GlobalDataHolder.maxTodoNameLength +" !");
    }
}
