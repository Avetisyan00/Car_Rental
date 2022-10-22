package com.example.carrental.repository;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByCategory(Category category);
    Page<Car> findAll(Pageable pageable);

    Optional<Car> findById(int id);
}
