package com.example.carrental.service.impl;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Category;
import com.example.carrental.entity.Image;
import com.example.carrental.entity.User;
import com.example.carrental.repository.CarDetailRepository;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.UserRepository;
import com.example.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarDetailRepository carDetailRepository;


    @Value("${car.rental.images.folder}")
    private String folderPath;

    public List<Car> findAll() {
        log.info("Find all cars from the database");
        return carRepository.findAll();
    }

    public List<Car> findAllByCategory(Category category) {
        log.info("Find all cars by category from the database");
        return carRepository.findAllByCategory(category);
    }

    public byte[] getCarService(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    public void deleteById(int id) {
        carRepository.deleteById(id);
        log.info("The car has been deleted the id{} ", id);
        List<Image> allByCarId = carDetailRepository.findAll();
        for (Image image : allByCarId) {
            if (image.getCar() == null) {
                carDetailRepository.deleteById(image.getId());
            }
        }
    }

    public void saveCar(Car car, MultipartFile file, int dealerId) {
        try {
            if (!file.isEmpty() && file.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File newFile = new File(folderPath + File.separator + fileName);
                file.transferTo(newFile);
                car.setPicUrl(fileName);
            }
            Optional<User> byId = userRepository.findById(dealerId);
            if (byId.isPresent() && car.getDealer() == null) {
                car.setDealer(byId.get());
            }
            carRepository.save(car);
            log.info("The car has been saved");
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<Car> findById(int id) {
        log.info("Find car by id {}", id + " from the database");
        return carRepository.findById(id);
    }

    public Page<Car> Search(Pageable pageable, String keyword) {
        if (keyword != null) {
            log.info("Find all cars from the database which keyword is {}", keyword);
            return carRepository.searchCarByNameOrModel(pageable, keyword);
        }
        return carRepository.findAll(pageable);
    }

    public List<Car> getByDealer(int dealerId) {
        List<Car> carByDealerId = carRepository.findCarByDealerId(dealerId);
        if (!carByDealerId.isEmpty()) {
            log.info("Find cars by dealerId {} ", dealerId + " from the database");
            return carByDealerId;
        }
        return carRepository.findAll();
    }
}
