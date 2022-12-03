package com.example.carrental.controller;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Category;
import com.example.carrental.security.CurrentUser;
import com.example.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CarController {

    private final CarService carService;


    /**
     * This method show cars
     */
    @GetMapping("/cars")
    public String cars(@RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size,
                       @RequestParam(required = false, name = "keyword") String keyword, ModelMap modelMap) {
        log.info("/cars has been called");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Car> cars = carService.Search(PageRequest.of(currentPage - 1, pageSize), keyword);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("keyword", keyword);
        int totalPages = cars.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        return "cars";
    }

    /**
     * This method gives car dealer
     */
    @GetMapping("/cars/getByDealer")
    public String getByDealer(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser,
                              @RequestParam("dealerId") int dealerId) {
        log.info("/cars/getByDealer has been called by{}", currentUser.getUser().getName());
        if (currentUser.getUser().getId() == dealerId) {
            modelMap.addAttribute("cars", carService.getByDealer(dealerId));
            return "cars";
        }
        return "redirect:/cars";
    }

    /**
     * This method gives car category
     */
    @GetMapping("/cars/getByCategory")
    public String getCarsByCategory(ModelMap modelMap, @RequestParam("category") Category category) {
        log.info("/cars/category has been called");
        List<Car> cars = carService.findAllByCategory(category);
        modelMap.addAttribute("cars", cars);
        return "cars";
    }

    @GetMapping("/cars/add")
    public String carsAddPage() {
        return "addCar";
    }

    @PostMapping("/cars/add")
    public String carsAdd(@ModelAttribute Car car,
                          @RequestParam(name = "carImage") MultipartFile file,
                          @RequestParam("dealerId") int dealerId) {
        log.info("/cars/add has been called");
        carService.saveCar(car, file, dealerId);
        return "redirect:/cars";
    }

    @GetMapping(value = "/cars/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return carService.getCarService(fileName);
    }

    @GetMapping("/cars/delete")
    public String delete(@RequestParam("id") int id) {
        log.info("/cars/delete has been called");
        carService.deleteById(id);
        return "redirect:/cars";

    }

    @GetMapping("/cars/edit")
    public String editPage(@RequestParam("id") int id, ModelMap modelMap) {
        log.info("/cars/edit has been called");
        Optional<Car> carOptional = carService.findById(id);
        if (carOptional.isEmpty()) {
            return "redirect:/cars";
        }
        modelMap.addAttribute("car", carOptional.get());
        return "editCar";
    }

    /**
     * This method changes car data
     */
    @PostMapping("/cars/edit")
    public String edit(@ModelAttribute Car car,
                       @RequestParam("dealerId") int dealerId,
                       @RequestParam(name = "carImage") MultipartFile file,
                       @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("/cars/edit has been called by {} ", currentUser.getUser().getName());
        if (currentUser.getUser().getId() == dealerId) {
            carService.saveCar(car, file, dealerId);
        }
        return "redirect:/cars";
    }


}