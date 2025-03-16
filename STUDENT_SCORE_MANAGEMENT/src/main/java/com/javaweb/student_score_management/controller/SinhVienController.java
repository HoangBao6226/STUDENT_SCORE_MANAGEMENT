package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.entity.SinhVienEntity;
import com.javaweb.student_score_management.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sinhvien")
public class SinhVienController {

    @Autowired
    private SinhVienRepository sinhVienRepository;


    @GetMapping("/index")
    public String sinhVienindex() {
        return "sinhvien/index";
    }

    @GetMapping("/{maSV}/diem")
    public ResponseEntity<?> getDiemBySinhVien(@PathVariable Integer maSV) {
        SinhVienEntity sinhVien = sinhVienRepository.findById(maSV).orElse(null);
        if (sinhVien == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sinh viên không tồn tại!");
        }
        return ResponseEntity.ok(sinhVien.getListDiem());
    }
}