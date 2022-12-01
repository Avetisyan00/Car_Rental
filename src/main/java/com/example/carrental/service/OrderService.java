package com.example.carrental.service;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Order;

import java.util.List;

public interface OrderService {

    void save(int carId, Order order);

    List<Order> findAllByCar_id(int id);
}
