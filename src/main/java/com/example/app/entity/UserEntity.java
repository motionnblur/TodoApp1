package com.example.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @Column(name = "user_entity_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userPassword;
}
