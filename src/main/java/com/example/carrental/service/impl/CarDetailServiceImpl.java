package com.example.carrental.service.impl;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Image;
import com.example.carrental.repository.CarDetailRepository;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.service.CarDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
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
public class CarDetailServiceImpl implements CarDetailService {

    private final CarDetailRepository carDetailRepository;
    private final CarRepository carRepository;

    @Value("${car.rental.images.folder}")
    private String folderPath;

    public void save(int carId, MultipartFile[] files) {
        int index = 0;
        Optional<Car> byId = carRepository.findById(carId);
        if (byId.isPresent()) {
            for (MultipartFile file : files) {
                index++;
                if (!file.isEmpty() && file.getSize() > 0) {
                    if (file.getContentType() != null && file.getContentType().contains("image")) {
                        try {
                            Image newImage = new Image();
                            newImage.setCar(byId.get());
                            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                            File newFile = new File(folderPath + File.separator + fileName);
                            file.transferTo(newFile);
                            newImage.setPicUrl(fileName);
                            carDetailRepository.save(newImage);
                            log.info("The image has been saved and carId{}", carId);
                            if (index == 5) {
                                break;
                            }
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            throw new RuntimeException(e.getMessage());

                        }
                    }
                }
            }
        }

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

    public List<Image> findAllByCar(int id) {
        log.info("Find all images by carId {}", id + " from the database");
        return carDetailRepository.findAllByCar_Id(id);
    }

    public void delete(int id) {
        carDetailRepository.deleteById(id);
        log.info("The image has been deleted the id {}", id);
    }
}
