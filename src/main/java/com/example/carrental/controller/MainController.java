package com.example.carrental.controller;

import com.example.carrental.entity.Car;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CarService carService;

    @GetMapping("/")
    public String mainPage(ModelMap modelMap) {
        List<Car> cars = carService.findAll();
        modelMap.addAttribute("cars",cars);
        return "index";
    }
}




