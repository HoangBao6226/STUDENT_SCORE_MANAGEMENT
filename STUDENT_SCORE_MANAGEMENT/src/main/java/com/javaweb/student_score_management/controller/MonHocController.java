package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.DTO.DangKyMonHocDTO;
import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.DTO.MonHocDTO;
import com.javaweb.student_score_management.DTO.XoaMonHocDTO;
import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.service.implement.DiemService;
import com.javaweb.student_score_management.service.implement.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class MonHocController {
    @Autowired
    private DiemService diemService;

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

    //SV
    @PostMapping("/sinhvien/dangky")
    public ResponseEntity<Map<String, Object>> dangKyMonHoc(@RequestBody DangKyMonHocDTO dangKyMonHocDTO) {
        Map<String, Object> response = new HashMap<>();
        Integer maSV = dangKyMonHocDTO.getMaSV();
        List<Integer> maMHList = dangKyMonHocDTO.getMaMHList();

        try {
            for (Integer maMH : maMHList) {
                diemService.dangKyMonHoc(maSV, maMH);
            }
            response.put("message", "Đăng ký môn học thành công!");

            // Lấy danh sách điểm mới với DiemDTO
            List<DiemDTO> diemList = diemService.getDiemBySinhVienID(maSV);
            response.put("diemList", diemList);
            response.put("totalCourses", diemList.size());
        } catch (Exception e) {
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/sinhvien/xoa")
    public ResponseEntity<Map<String, Object>> xoaNhieuMonHoc(@RequestBody XoaMonHocDTO xoaMonHocDTO) {
        Map<String, Object> response;
        try {
            response = diemService.xoaNhieuMonHoc(xoaMonHocDTO.getMaSV(), xoaMonHocDTO.getMaMHList());
        } catch (Exception e) {
            response = new HashMap<>();
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


}
