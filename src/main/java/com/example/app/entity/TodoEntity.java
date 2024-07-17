package com.example.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoEntity {
    @Id
    @Column(name = "todo_entity_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String todoName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_entity_id", referencedColumnName = "todo_entity_id")
    private List<TodoItemEntity> todoItemEntities;
}
