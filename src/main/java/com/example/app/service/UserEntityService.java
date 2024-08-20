package com.example.app.service;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.UserEntityDto;
import com.example.app.entity.UserEntity;
import com.example.app.helper.AuthHelper;
import com.example.app.repository.UserEntityRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEntityService {
    @Autowired
    protected UserEntityRepository userEntityRepository;
    @Autowired
    protected AuthHelper authHelper;

    public UserEntity getUserEntity(String userName) throws Exception {
        UserEntity userEntity = userEntityRepository.findByUserName(userName);
        if(userEntity == null) throw new Exception("A todo with that name couldn't be found");

        return userEntity;
    }

    public UserEntity saveUserEntity(UserEntityDto userEntityDto) {
        UserEntity userEntityTemp = new UserEntity();
        userEntityTemp.setUserName(userEntityDto.getName());
        userEntityTemp.setUserPassword(userEntityDto.getPassword());

        return userEntityRepository.save(userEntityTemp);
    }

    public void loginUser(HttpServletResponse response, UserEntityDto userEntityDto) throws Exception {
        UserEntity userEntity = userEntityRepository.findByUserName(userEntityDto.getName());
        if(userEntity == null) throw new Exception("Login error");

        String sessionId = UUID.randomUUID().toString();

        Cookie cookie = new Cookie("SESSION_ID", sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60* GlobalDataHolder.cookieExpireMinute); // 60 minutes

        response.addCookie(cookie);

        authHelper.addToAuthList(sessionId, userEntity.getUserName());

        if(!userEntity.getUserPassword().equals(userEntityDto.getPassword()))
            throw new Exception("Login error");
    }
    public void authUser(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;

        if(cookies == null) throw new Exception("Auth error");
        for(Cookie c : cookies){
            String cookieName = c.getName();
            if(cookieName.equals("SESSION_ID")){
                cookieValue = c.getValue();
                break;
            }
        }
        if(cookieValue == null) throw new Exception("Auth error");
        String userName = authHelper.getUserNameFromAuthList(cookieValue);
        if(userName == null) throw new Exception("Auth error");
    }

    public String getUserName(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;

        if(cookies == null) throw new Exception("Auth error");
        for(Cookie c : cookies){
            String cookieName = c.getName();
            if(cookieName.equals("SESSION_ID")){
                cookieValue = c.getValue();
                break;
            }
        }
        if(cookieValue == null) throw new Exception("Auth error");
        String userName = authHelper.getUserNameFromAuthList(cookieValue);
        if(userName == null) throw new Exception("Auth error");

        return userName;
    }
}
