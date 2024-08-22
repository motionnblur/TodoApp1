package com.example.app.repository;

import com.example.app.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoEntityRepository extends JpaRepository<TodoEntity, Long> {
    TodoEntity findByTodoName(String todoName);
}
