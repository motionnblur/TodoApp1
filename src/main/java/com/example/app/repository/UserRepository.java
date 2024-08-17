package com.example.app.repository;

import com.example.app.entity.TodoEntity;
import com.example.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    TodoEntity findByUserName(String userName);
}
