package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.DTO.DangKyMonHocDTO;
import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.entity.MonHocEntity;
import com.javaweb.student_score_management.service.implement.DiemService;
import com.javaweb.student_score_management.service.implement.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sinhvien")
public class MonHocController {
    @Autowired
    private DiemService diemService;

    @Autowired
    private MonHocService monHocService;

    @GetMapping("/danhsach")
    public ResponseEntity<List<MonHocEntity>> getAllMonHoc() {
        List<MonHocEntity> danhSachMonHoc = monHocService.getAllMonHoc();
        return ResponseEntity.ok(danhSachMonHoc);
    }

    @PostMapping("/dangky")
    public ResponseEntity<Map<String, Object>> dangKyMonHoc(
            @RequestBody DiemDTO diemDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            diemService.dangKyMonHoc(diemDTO.getMaSV(), diemDTO.getMaMH());
            response.put("message", "Đăng ký môn học thành công!");

            // Lấy danh sách môn học đã đăng ký của sinh viên
            List<DiemEntity> diemList   = diemService.getDiemDetailsBySinhVienID(diemDTO.getMaSV());
            response.put("diemList", diemList);

        } catch (Exception e) {
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/xoa")
    public ResponseEntity<Map<String, Object>> xoaMonHoc(@RequestBody DangKyMonHocDTO request) {
        Map<String, Object> response = new HashMap<>();

        try {
            diemService.xoaMonHoc(request.getMaSV(), request.getMaMH());
            response.put("message", "Xóa môn học thành công!");
        } catch (Exception e) {
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}
