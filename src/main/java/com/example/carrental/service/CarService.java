package com.example.carrental.service;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> findAll();

    List<Car> findAllByCategory(Category category);

    void deleteById(int id);

    byte[] getCarService(String fileName);

    void saveCar(Car car, MultipartFile file, int dealerId);

    Optional<Car> findById(int id);

    Page<Car> Search(Pageable pageable, String keyword);

    List<Car> getByDealer(int dealerId);
}
