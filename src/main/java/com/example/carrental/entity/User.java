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
    private int age;
    private String phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String driverLicense;
    private double price;
    private int rating;
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
