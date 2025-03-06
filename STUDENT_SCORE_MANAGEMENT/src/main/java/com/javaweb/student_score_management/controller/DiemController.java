package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.service.implement.DiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class DiemController {
    @Autowired
    private DiemService diemService;

    @GetMapping("/sinhvien/bangdiem")
    public ResponseEntity<?> xemBangDiem(@RequestParam("maSV") Integer maSV) {
        try {
            List<DiemEntity> diemList = diemService.getDiembySinhVienID(maSV);
            return ResponseEntity.ok(diemList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
