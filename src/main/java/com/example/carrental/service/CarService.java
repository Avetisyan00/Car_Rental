package com.example.carrental.service;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> findAllByCategory(Category category);

    List<Car> findAll();

    void deleteById(int id);

    Page<Car> findAllPg(Pageable pageable);

    void saveCar(Car car, MultipartFile file);

    Optional<Car> findById(int id);

     byte[] getCarService(String fileName);
}
