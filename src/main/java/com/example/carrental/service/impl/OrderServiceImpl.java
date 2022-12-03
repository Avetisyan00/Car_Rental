package com.example.carrental.service.impl;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Order;
import com.example.carrental.entity.User;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.OrderRepository;
import com.example.carrental.repository.UserRepository;
import com.example.carrental.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    public void save(int carId, Order order) {
        Optional<Car> byId = carRepository.findById(carId);
        if (byId.isPresent()) {
            order.setAmount(byId.get().getPricePerHour());
            order.setCar(byId.get());
            orderRepository.save(order);
            log.info("The car has been saved");
        }
    }


    public List<Order> findAllByCar_id(int id) {
        return orderRepository.findAllByCar_Id(id);
    }
}
