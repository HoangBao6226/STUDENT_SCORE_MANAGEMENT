package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.DTO.MonHocDTO;
import com.javaweb.student_score_management.service.implement.DiemService;
import com.javaweb.student_score_management.service.implement.ExcelService;
import com.javaweb.student_score_management.service.implement.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/giangvien")
public class GiangVienController {

    @Autowired
    private MonHocService monHocService;

    @Autowired
    private DiemService diemService;

    @Autowired
    private ExcelService excelService;

    //Trang chủ là index
    @GetMapping("/index")
    public String giangVienIndex() {
        return "giangvien/index";
    }

    // Lấy MH mình dạy
    @GetMapping("/monhoc/{maGV}")
    public ResponseEntity<List<MonHocDTO>> getMonHocByMaGV(@PathVariable int maGV) {
        List<MonHocDTO> dsMonHoc = monHocService.getMonHocByMaGV(maGV);
        return ResponseEntity.ok(dsMonHoc);
    }

    // Lấy SV theo học môn mình dạy
    @GetMapping("/monhoc/{maGV}/{maMH}")
    public ResponseEntity<List<DiemDTO>> getSVByMaMHAndMaGV(@PathVariable int maMH, @PathVariable int maGV) {
        List<DiemDTO> dsDiem = diemService.getSV_Diem(maMH, maGV);
        return ResponseEntity.ok(dsDiem);
    }

    // Nhập điểm từ file Excel
    @PostMapping("/excel")
    public ResponseEntity<?> importGrades(@RequestParam(name = "file") MultipartFile file) {
        try {
            List<DiemDTO> importedGrades = excelService.importExcel(file);
            return ResponseEntity.ok(importedGrades);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Lỗi nhập file: " + e.getMessage());
        }
    }
}
