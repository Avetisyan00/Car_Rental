package com.example.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Transmission transmission;
    @Enumerated(value = EnumType.STRING)
    private DriveUnit driveUnit;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    @Enumerated(value = EnumType.STRING)
    private SteeringWheel steeringWheel;
    @Enumerated(value = EnumType.STRING)
    private FuelType fuelType;
    private String color;

    private int productionYear;
    private String picUrl;
    private double pricePerHour;
    @ManyToOne
    private User dealer;
}

