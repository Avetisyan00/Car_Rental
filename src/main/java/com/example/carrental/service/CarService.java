package com.example.carrental.service;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Category;
import com.example.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;


    @Value("${car.rental.images.folder}")
    private String folderPath;

    public Page<Car> findAllPg(Pageable pageable)
    {
        return carRepository.findAll(pageable);
    }
    public List<Car> findAll(){
        return carRepository.findAll();
    }

    public List<Car> findAllByCategory(Category category) {
        return carRepository.findAllByCategory(category);
    }
    public byte[] getCarService(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    public void saveCar(Car car, MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            car.setPicUrl(fileName);
        }
        carRepository.save(car);
    }

    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }
}
