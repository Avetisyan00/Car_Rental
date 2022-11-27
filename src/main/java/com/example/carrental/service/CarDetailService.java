package com.example.carrental.service;

import com.example.carrental.entity.Image;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarDetailService {
    void save(int carId, MultipartFile[] files);
    byte[] getCarService(String fileName);
    List<Image> findAllByCar(int id);
    void delete(int id);
}
