package com.example.app.service;

import com.example.app.dto.UserEntityDto;
import com.example.app.entity.UserEntity;
import com.example.app.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService {
    @Autowired
    protected UserEntityRepository userEntityRepository;

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
}
