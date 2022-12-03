package com.example.carrental.service.impl;

import com.example.carrental.entity.*;
import com.example.carrental.repository.CarDetailRepository;
import com.example.carrental.service.CarDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarDetailServiceImplTest {

    @Autowired
    private CarDetailService carDetailService;
    @MockBean
    private CarDetailRepository carDetailRepository;
    @Test
    void save() {
        Image image = Image.builder()
                .id(1)
                .picUrl("BmwImage")
                .car(new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User()))
                .build();
        when(carDetailRepository.save(any())).thenReturn(image);
        MultipartFile [] file = new MockMultipartFile[]{new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg","2023-bmw-x5-front-1660572027.jpg","image","C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes())};
        carDetailService.save(image.getCar().getId(),file);
        verify(carDetailRepository,times(1)).save(any());

    }
    @Test
    void saveContentTypeNull() {
        Image image = Image.builder()
                .id(1)
                .picUrl("BmwImage")
                .car(new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User()))
                .build();
        when(carDetailRepository.save(any())).thenReturn(image);
        MultipartFile [] file = new MockMultipartFile[]{new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg","C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes())};
        carDetailService.save(image.getCar().getId(),file);
        verify(carDetailRepository,times(0)).save(any());

    }
    @Test
    void saveContentTypeIsNotImage() {
        Image image = Image.builder()
                .id(1)
                .picUrl("BmwImage")
                .car(new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User()))
                .build();
        when(carDetailRepository.save(any())).thenReturn(image);
        MultipartFile [] file = new MockMultipartFile[]{new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg","2023-bmw-x5-front-1660572027.jpg","txt","C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes())};
        carDetailService.save(image.getCar().getId(),file);
        verify(carDetailRepository,times(0)).save(any());

    }
    @Test
    void saveIfFileEmpty() {
        Image image = Image.builder()
                .id(1)
                .picUrl("BmwImage")
                .car(new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User()))
                .build();
        when(carDetailRepository.save(any())).thenReturn(image);
        assertThrows(RuntimeException.class,()-> carDetailService.save(image.getCar().getId(),null));
        verify(carDetailRepository,times(0)).save(any());

    }
    @Test
    void findAllByCar() {
        List<Image> images = Arrays.asList(
                new Image(1,"BmwImage",new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())) ,
        new Image(2,"BmwImage2",new Car(3, "bmw1", "X7", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 90, new User()))
        );
        when(carDetailRepository.findAllByCar_Id(anyInt())).thenReturn(images);
        for (Image image : images) {
            carDetailService.findAllByCar(image.getCar().getId());
        }
        verify(carDetailRepository,times(2)).findAllByCar_Id(anyInt());

    }

    @Test
    void delete() {
        Image image = Image.builder()
                .id(3)
                .build();
        when(carDetailRepository.findById(any())).thenReturn(Optional.of(image));
        carDetailService.delete(image.getId());
        verify(carDetailRepository,times(1)).deleteById(any());
    }
}