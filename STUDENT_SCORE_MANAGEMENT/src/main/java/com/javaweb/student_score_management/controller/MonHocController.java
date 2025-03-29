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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class MonHocController {
    @Autowired
    private DiemService diemService;

    @Autowired
    private MonHocService monHocService;
    //Admin

//    @GetMapping("/admin/monhoc")
//    public List<MonHocDTO> getAllMonHoc() {
//        return monHocService.getAllMonHoc();
//    }
//
//    @GetMapping("/admin/monhoc/{id}")
//    public MonHocDTO getMonHocById(@PathVariable Integer id) {
//        return monHocService.getMonHocById(id);
//    }
//
//    @PostMapping("/admin/monhoc")
//    public boolean createMonHoc(@RequestBody MonHocDTO monHocDTO) {
//        return monHocService.createMonHoc(monHocDTO);
//    }
//
//    @PutMapping("/admin/monhoc/{id}")
//    public boolean updateMonHoc(@PathVariable Integer id, @RequestBody MonHocDTO monHocDTO) {
//        return monHocService.updateMonHoc(id, monHocDTO);
//    }
//
//    @DeleteMapping("/admin/monhoc/{id}")
//    public boolean deleteMonHoc(@PathVariable Integer id) {
//        return monHocService.deleteMonHoc(id);
//    }
    // Hiển thị danh sách môn học (View)
    @GetMapping("/admin/monhoc")
    public String listMonHoc(Model model) {
        List<MonHocDTO> list = monHocService.getAllMonHoc();
        model.addAttribute("list", list);
        return "admin/monhoc/index"; // templates/admin/monhoc/index.html
    }

    // API lấy danh sách môn học (JSON)
    @GetMapping("/api/monhoc")
    @ResponseBody
    public ResponseEntity<List<MonHocDTO>> getAllMonHoc() {
        return ResponseEntity.ok(monHocService.getAllMonHoc());
    }

    // Hiển thị form thêm môn học (View)
    @GetMapping("/admin/monhoc/add")
    public String showAddForm(Model model) {
        model.addAttribute("monHoc", new MonHocDTO());
        return "admin/monhoc/addMonHoc"; // templates/admin/monhoc/addMonHoc.html
    }

    // API lấy môn học theo ID (JSON)
    @GetMapping("/api/monhoc/{id}")
    public ResponseEntity<?> getMonHocById(@PathVariable Integer id) {
        MonHocDTO monHoc = monHocService.getMonHocById(id);
        if (monHoc != null) {
            return ResponseEntity.ok(monHoc);
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Không tìm thấy môn học với ID: " + id + "\"}");
        }
    }

    // API thêm môn học (JSON)
    @PostMapping("/api/monhoc")
    public ResponseEntity<String> createMonHoc(@RequestBody MonHocDTO monHocDTO) {
        if (monHocDTO.getTenMH() == null || monHocDTO.getTenMH().isEmpty()) {
            return ResponseEntity.badRequest().body("{\"message\": \"Tên môn học không được để trống\"}");
        }
        if (monHocDTO.getSoTinChi() == null || monHocDTO.getSoTinChi() <= 0) {
            return ResponseEntity.badRequest().body("{\"message\": \"Số tín chỉ phải lớn hơn 0\"}");
        }
        if (monHocDTO.getMaGV() == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Mã giảng viên không được để trống\"}");
        }
        if (monHocService.existsByTenMH(monHocDTO.getTenMH())) {
            return ResponseEntity.badRequest().body("{\"message\": \"Tên môn học đã tồn tại!\"}");
        }
        boolean created = monHocService.createMonHoc(monHocDTO);
        if (created) {
            return ResponseEntity.ok("{\"message\": \"Tạo môn học thành công!\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Tạo môn học thất bại!\"}");
        }
    }

    // Hiển thị form chỉnh sửa môn học (View)
    @GetMapping("/admin/monhoc/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        MonHocDTO monHoc = monHocService.getMonHocById(id);
        if (monHoc == null) {
            throw new IllegalArgumentException("Không tìm thấy môn học với ID: " + id);
        }
        model.addAttribute("monHoc", monHoc);
        return "admin/monhoc/editMonHoc"; // templates/admin/monhoc/editMonHoc.html
    }

    // API cập nhật môn học (JSON)
    @PutMapping("/api/monhoc/{id}")
    @ResponseBody
    public ResponseEntity<String> updateMonHoc(@PathVariable Integer id, @RequestBody MonHocDTO monHocDTO) {
        monHocDTO.setMaMH(id);

        if (monHocService.updateMonHoc(id, monHocDTO)) {
            return ResponseEntity.ok("{\"message\": \"Cập nhật môn học thành công!\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Cập nhật môn học thất bại!\"}");
        }
    }

    // API xóa môn học (JSON)
    @DeleteMapping("/api/monhoc/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteMonHoc(@PathVariable Integer id) {
        boolean deleted = monHocService.deleteMonHoc(id);
        if (deleted) {
            return ResponseEntity.ok("{\"message\": \"Xóa môn học thành công!\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Không tìm thấy môn học với ID: " + id + "\"}");
        }
    }

    //SV
    @GetMapping("monhoc/search")
    public ResponseEntity<Map<String, Object>> searchMonHocByTenGanDung(@RequestParam String keyword) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MonHocDTO> monHocList = monHocService.findMonHocByTenMHGanDung(keyword);
            response.put("monHocList", monHocList);
            response.put("message", "Tìm kiếm thành công");
            response.put("count", monHocList.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Lỗi khi tìm kiếm: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/sinhvien/dangky")
    public ResponseEntity<Map<String, Object>> dangKyMonHoc(@RequestBody DangKyMonHocDTO dangKyMonHocDTO) {
        Map<String, Object> response = new HashMap<>();
        Integer maSV = dangKyMonHocDTO.getMaSV();
        List<Integer> maMHList = dangKyMonHocDTO.getMaMHList();
        List<Integer> monHocBiChan = new ArrayList<>();
        List<Integer> monHocDangKyThanhCong = new ArrayList<>();

        try {
            for (Integer maMH : maMHList) {
                List<DiemEntity> diemList = diemService.findByMaSVAndMaMH(maSV, maMH);

                // Nếu môn đã tồn tại và có điểm = null, thì không cho đăng ký lại
                boolean daCoDiemNull = diemList.stream().anyMatch(diem -> diem.getDiem() == null);
                if (!diemList.isEmpty() && daCoDiemNull) {
                    monHocBiChan.add(maMH);
                    continue; // Bỏ qua môn này
                }

                // Nếu môn hợp lệ, tiến hành đăng ký
                diemService.dangKyMonHoc(maSV, maMH);
                monHocDangKyThanhCong.add(maMH);
            }

            response.put("message", "Đăng ký môn học hoàn tất!");
            response.put("monHocBiChan", monHocBiChan);
            response.put("monHocDangKyThanhCong", monHocDangKyThanhCong);

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
