package com.example.carrental.repository;

import com.example.carrental.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarDetailRepository extends JpaRepository<Image,Integer> {

    List<Image> findAllByCar_Id(int id);
    Optional<Image> deleteByCar_Id(int carId);
}
