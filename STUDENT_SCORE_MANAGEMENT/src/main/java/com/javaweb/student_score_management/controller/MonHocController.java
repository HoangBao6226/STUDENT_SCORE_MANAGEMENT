package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.DTO.MonHocDTO;
import com.javaweb.student_score_management.service.implement.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MonHocController {
    @Autowired
    private MonHocService monHocService;
    //Admin
    @GetMapping("/admin/monhoc")
    public List<MonHocDTO> getAllMonHoc() {
        return monHocService.getAllMonHoc();
    }

    @GetMapping("/admin/monhoc/{id}")
    public MonHocDTO getMonHocById(@PathVariable Integer id) {
        return monHocService.getMonHocById(id);
    }

    @PostMapping("/admin/monhoc")
    public boolean createMonHoc(@RequestBody MonHocDTO monHocDTO) {
        return monHocService.createMonHoc(monHocDTO);
    }

    @PutMapping("/admin/monhoc/{id}")
    public boolean updateMonHoc(@PathVariable Integer id, @RequestBody MonHocDTO monHocDTO) {
        return monHocService.updateMonHoc(id, monHocDTO);
    }

    @DeleteMapping("/admin/monhoc/{id}")
    public boolean deleteMonHoc(@PathVariable Integer id) {
        return monHocService.deleteMonHoc(id);
    }
}
