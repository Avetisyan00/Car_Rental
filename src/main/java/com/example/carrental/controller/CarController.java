package com.example.carrental.controller;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Category;
import com.example.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
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
public class CarController {

    private final CarService carService;


    @GetMapping("/cars")
    public String cars(@RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size,
                       ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Car> cars = carService.findAllPg(PageRequest.of(currentPage - 1, pageSize));
        modelMap.addAttribute("cars", cars);
        int totalPages = cars.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        return "cars";
    }

    @GetMapping("/cars/getByCategory")
    public String getCarsByCategory(ModelMap modelMap, @RequestParam("category") Category category) {
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
                          @RequestParam(name = "carImage") MultipartFile file) {
        carService.saveCar(car, file);
        return "redirect:/cars";
    }

    @GetMapping(value = "/cars/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return carService.getCarService(fileName);
    }

    @GetMapping("/cars/delete")
    public String delete(@RequestParam("id") int id) {
        carService.deleteById(id);
        return "redirect:/cars";
    }

    @GetMapping("/cars/edit")
    public String editPage(@RequestParam("id") int id, ModelMap modelMap) {
        Optional<Car> carOptional = carService.findById(id);
        if (carOptional.isEmpty()) {
            return "redirect:/cars";
        }
        modelMap.addAttribute("car", carOptional.get());
        return "editCar";
    }

    @PostMapping("/cars/edit")
    public String edit(@ModelAttribute Car car,
                       @RequestParam(name = "carImage") MultipartFile file) {
        carService.saveCar(car, file);
        return "redirect:/cars";
    }
}
