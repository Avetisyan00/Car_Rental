package com.example.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "car_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderEnd;
    @ManyToOne
    private User driver;
    @ManyToOne
    private Car car;
    private double amount;


}
