package com.example.carrental.service.impl;

import com.example.carrental.entity.*;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarServiceImplTest {

    @Autowired
    private CarService carService;
    @MockBean
    private CarRepository carRepository;

    @Test
    void findAll() {
        List<Car> cars = Arrays.asList(
                new Car(1, "bmw", "X5", Transmission.AUTO, DriveUnit.FWD, Category.ELECTRIC, SteeringWheel.LEFT, FuelType.DIESEL,
                        "White", 2008, "carImage", 78, new User()),
                new Car(2, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.ELECTRIC, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())
        );
        when(carRepository.findAll()).thenReturn(cars);
        carService.findAll();
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void findAllByCategory() {
        List<Car> cars = Arrays.asList(
                new Car(1, "bmw", "X5", Transmission.AUTO, DriveUnit.FWD, Category.ELECTRIC, SteeringWheel.LEFT, FuelType.DIESEL,
                        "White", 2008, "carImage", 78, new User()),
                new Car(2, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())
        );
        when(carRepository.findAllByCategory(any())).thenReturn(cars);
        for (Car car : cars) {
            carService.findAllByCategory(car.getCategory());
        }
        verify(carRepository, times(2)).findAllByCategory(any());

    }

    @Test
    void deleteById() {
        Car car = Car.builder()
                .id(3)
                .build();
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        carService.deleteById(car.getId());
        verify(carRepository, times(1)).deleteById(any());
    }

    @Test
    void deleteById0() {
        Car car = Car.builder()
                .id(0)
                .build();
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        assertThrows(RuntimeException.class, () -> carService.deleteById(car.getId()));
        verify(carRepository, times(0)).deleteById(any());
    }

    @Test
    void saveCar() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .pricePerHour(2020)
                .picUrl("mercedesCarImage")
                .pricePerHour(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        MultipartFile file = new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg", "2023-bmw-x5-front-1660572027.jpg", "image", "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes());
        carService.saveCar(car, file, car.getDealer().getId());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void saveCarContentTypeNull() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .pricePerHour(2020)
                .picUrl("mercedesCarImage")
                .pricePerHour(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        MultipartFile file = new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg", "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes());
        carService.saveCar(car, file, car.getDealer().getId());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void saveCarContentTypeIsNotImage() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .pricePerHour(2020)
                .picUrl("mercedesCarImage")
                .pricePerHour(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        MultipartFile file = new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg", "2023-bmw-x5-front-1660572027.jpg", "txt", "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes());
        carService.saveCar(car, file, car.getDealer().getId());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void saveCarNull() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .pricePerHour(2020)
                .picUrl("mercedesCarImage")
                .pricePerHour(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        assertThrows(RuntimeException.class, () -> carService.saveCar(null, null, 0));
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void findById() {
        Car car = Car.builder()
                .id(3)
                .build();
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        carService.findById(car.getId());
        verify(carRepository, times(1)).findById(anyInt());
    }

    @Test
    void findById0() {
        Car car = Car.builder()
                .id(0)
                .build();
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        assertThrows(RuntimeException.class, () -> carService.findById(car.getId()));
        verify(carRepository, times(0)).findById(anyInt());
    }

    @Test
    void search() {
        List<Car> cars = Arrays.asList(
                new Car(1, "bmw", "X5", Transmission.AUTO, DriveUnit.FWD, Category.ELECTRIC, SteeringWheel.LEFT, FuelType.DIESEL,
                        "White", 2008, "carImage", 78, new User()),
                new Car(2, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.ELECTRIC, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())
        );
        Page<Car> pageable = new PageImpl<>(cars);
        when(carRepository.searchCarByNameOrModel(any(), any())).thenReturn(pageable);
        for (Car car : pageable) {
            carService.Search(PageRequest.of(1, 5), car.getModel());
        }
        verify(carRepository, times(2)).searchCarByNameOrModel(any(), any());

    }

    @Test
    void getByDealer() {
        List<Car> cars = Arrays.asList(
                new Car(1, "bmw", "X5", Transmission.AUTO, DriveUnit.FWD, Category.ELECTRIC, SteeringWheel.LEFT, FuelType.DIESEL,
                        "White", 2008, "carImage", 78, new User()),
                new Car(2, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.ELECTRIC, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())
        );
        when(carRepository.findCarByDealerId(anyInt())).thenReturn(cars);
        for (Car car : cars) {
            carService.getByDealer(car.getDealer().getId());
        }
        verify(carRepository, times(2)).findCarByDealerId(anyInt());

    }
}