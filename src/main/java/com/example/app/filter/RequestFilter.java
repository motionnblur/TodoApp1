package com.example.app.filter;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import com.example.app.dto.UserEntityDto;
import com.example.app.helper.HttpServletRequestHelper;
import com.example.app.helper.ReaderHelper;
import com.example.app.helper.SecurityHelper;
import com.example.app.helper.StringHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class RequestFilter implements Filter {
    @Autowired ReaderHelper readerHelper;

    SecurityHelper securityHelper = new SecurityHelper();
    ObjectMapper objectMapper = new ObjectMapper();
    HttpServletRequestHelper httpServletRequestHelper;
    HttpServletRequest req;
    HttpServletResponse res;
    String requestUrl;

    FilterChain chain;
    ServletResponse response;

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.chain = chain;
        this.response = response;

        this.req = (HttpServletRequest) request;
        this.res = (HttpServletResponse) response;
        // The important part!! wrap the request:
        this.httpServletRequestHelper = new HttpServletRequestHelper(req);

        this.requestUrl = req.getRequestURL().toString();

        switch(req.getMethod()) {
            case "POST" -> handlePost();
            case "PUT" -> handlePut();
            case "GET" -> handleGet();
        }
    }

    private void handlePost() throws ServletException, IOException {
        switch (requestUrl) {
            case "http://localhost:8080/todo" -> {
                String requestBodyAsString = readerHelper.getStringFromInputStream(httpServletRequestHelper);
                TodoEntityDto todoEntityDto = objectMapper.readValue(requestBodyAsString, TodoEntityDto.class);

                if (securityHelper.securityCheckTodoEntity(todoEntityDto)) {
                    chain.doFilter(httpServletRequestHelper, response);
                    return;
                }

                res.setStatus(HttpStatus.BAD_REQUEST.value());
                res.getWriter().write("Todo or item name length can't be more than " + GlobalDataHolder.maxTodoNameLength + " !");
            }
            case "http://localhost:8080/todo/addItem" -> {
                String queryStr1 = httpServletRequestHelper.getParameter("todoName");
                String queryStr2 = httpServletRequestHelper.getParameter("item");

                if((!queryStr1.isEmpty() && !queryStr2.isEmpty()) &&
                        (securityHelper.securityCheckString(queryStr1) && securityHelper.securityCheckString(queryStr2))) {
                    chain.doFilter(httpServletRequestHelper, response);
                    return;
                }

                res.addHeader("Access-Control-Allow-Origin", "*");
                res.setStatus(HttpStatus.BAD_REQUEST.value());
                res.getWriter().write("fields shouldn't be empty");
            }
            case "http://localhost:8080/todo/deleteItem",
                 "http://localhost:8080/todo/markItem" -> chain.doFilter(httpServletRequestHelper, response);
        }
    }
    private void handlePut() throws ServletException, IOException {
        switch (requestUrl){
            case "http://localhost:8080/user" -> {
                StringHelper stringHelper = new StringHelper();

                String requestBodyAsString = readerHelper.getStringFromInputStream(httpServletRequestHelper);
                UserEntityDto userEntityDto = objectMapper.readValue(requestBodyAsString, UserEntityDto.class);
                String userName = userEntityDto.getName();

                if(stringHelper.checkIfStringLengthLessThan(GlobalDataHolder.maxUserNameLength, userName.length()))
                {
                    chain.doFilter(httpServletRequestHelper, response);
                    return;
                }

                res.setStatus(HttpStatus.BAD_REQUEST.value());
                res.getWriter().write("User name can't be more than "+ GlobalDataHolder.maxUserNameLength +" !");
            }
            case "http://localhost:8080/todo" -> {
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
            }
        }
    }
    private void handleGet() throws ServletException, IOException {
        if(req.getMethod().equals("GET") && requestUrl.equals("http://localhost:8080/todo/getAllTodo")){
            chain.doFilter(httpServletRequestHelper, response);
        }else if(req.getMethod().equals("GET") && requestUrl.equals("http://localhost:8080/user")){
            chain.doFilter(httpServletRequestHelper, response);
        }
        else if(req.getMethod().equals("GET") || req.getMethod().equals("DELETE")){
            StringHelper stringHelper = new StringHelper();

            String queryStr = httpServletRequestHelper.getParameter("todoName");
            if(stringHelper.checkIfStringLengthLessThan(GlobalDataHolder.maxTodoNameLength, queryStr.length()))
            {
                chain.doFilter(httpServletRequestHelper, response);
                return;
            }

            res.setStatus(HttpStatus.BAD_REQUEST.value());
            res.getWriter().write("Todo name can't be more than "+ GlobalDataHolder.maxTodoNameLength +" !");
        }else if(req.getMethod().equals("OPTIONS")){
            chain.doFilter(httpServletRequestHelper, response);
        }
        else {
            res.setStatus(HttpStatus.BAD_REQUEST.value());
            res.getWriter().write("Bad request");
        }
    }
}
