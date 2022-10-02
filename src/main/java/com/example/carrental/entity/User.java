package com.example.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String driverXp;
    private double price;
    private int rating;
    private String comment;
    @ManyToOne
    private Order order;
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
