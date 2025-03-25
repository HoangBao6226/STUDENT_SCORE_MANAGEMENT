package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.service.implement.DiemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class DiemController {
    private static final Logger logger = LoggerFactory.getLogger(DiemController.class);

    @Autowired
    private DiemService diemService;

    public DiemController(DiemService diemService) {
        this.diemService = diemService;
    }

    // Sinh Vien: Render trang Thymeleaf
    @GetMapping("/sinhvien/bangdiem")
    public String xemBangDiem(@RequestParam("maSV") Integer maSV, Model model) {
        List<DiemEntity> diemList = diemService.getDiemDetailsBySinhVienID(maSV);
        model.addAttribute("diemList", diemList);
        return "bangdiem";
    }

    // Admin: Render trang Thymeleaf nhúng React
    @GetMapping("/admin/diem")
    public String quanLyDiem(Model model) {
        List<DiemDTO> diemList = diemService.getAllDiem();
        model.addAttribute("danhSachDiem", diemList);
        return "admin/diem/index";
    }

    // API trả về JSON cho React gọi
    @GetMapping("/api/admin/diem")
    @ResponseBody
    public List<DiemDTO> getAllDiem() {
        List<DiemDTO> diemList = diemService.getAllDiem();
        return diemList;
    }

    @PostMapping("/api/admin/diem")
    @ResponseBody
    public ResponseEntity<String> addDiem(@RequestBody DiemDTO diemDTO) {
        logger.info("Dữ liệu điểm nhận được: {}", diemDTO);
        boolean created = diemService.createDiem(diemDTO);
        if (created) {
            return ResponseEntity.ok("Thêm điểm thành công!");
        } else {
            return ResponseEntity.badRequest().body("Thêm điểm thất bại!");
        }
    }

    @PutMapping("/api/admin/diem/{id}")
    @ResponseBody
    public ResponseEntity<String> updateDiem(@PathVariable Integer id, @RequestBody DiemDTO diemDTO) {
        logger.info("Cập nhật điểm ID {} với dữ liệu: {}", id, diemDTO);
        boolean updated = diemService.updateDiem(id, diemDTO);
        if (updated) {
            return ResponseEntity.ok("Cập nhật điểm thành công!");
        } else {
            return ResponseEntity.badRequest().body("Cập nhật điểm thất bại!");
        }
    }

    @DeleteMapping("/api/admin/diem/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteDiem(@PathVariable Integer id) {
        logger.info("Xóa điểm ID: {}", id);
        boolean deleted = diemService.deleteDiem(id);
        if (deleted) {
            return ResponseEntity.ok("Xóa điểm thành công!");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy điểm!");
        }
    }
}