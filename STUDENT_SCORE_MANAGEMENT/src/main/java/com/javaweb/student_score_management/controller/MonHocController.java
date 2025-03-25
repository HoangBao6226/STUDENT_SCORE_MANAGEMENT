package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.DTO.MonHocDTO;
import com.javaweb.student_score_management.service.implement.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MonHocController {

    @Autowired
    private MonHocService monHocService;

    // Render trang Thymeleaf nhúng React
    @GetMapping("/monhoc")
    public String showMonHocList(Model model) {
        // Lấy danh sách môn học để truyền vào Thymeleaf (nếu cần)
        List<MonHocDTO> danhSachMonHoc = monHocService.getAllMonHoc();
        model.addAttribute("danhSachMonHoc", danhSachMonHoc);
        return "admin/monhoc/index"; // Trả về src/main/resources/templates/admin/monhoc/index.html
    }

    // API trả về JSON cho React gọi
    @GetMapping("/api/monhoc")
    @ResponseBody
    public List<MonHocDTO> getAllMonHoc() {
        return monHocService.getAllMonHoc();
    }

    @GetMapping("/api/monhoc/{id}")
    @ResponseBody
    public MonHocDTO getMonHocById(@PathVariable Integer id) {
        return monHocService.getMonHocById(id);
    }

    @PostMapping("/api/monhoc")
    @ResponseBody
    public boolean createMonHoc(@RequestBody MonHocDTO monHocDTO) {
        return monHocService.createMonHoc(monHocDTO);
    }

    @PutMapping("/api/monhoc/{id}")
    @ResponseBody
    public boolean updateMonHoc(@PathVariable Integer id, @RequestBody MonHocDTO monHocDTO) {
        return monHocService.updateMonHoc(id, monHocDTO);
    }

    @DeleteMapping("/api/monhoc/{id}")
    @ResponseBody
    public boolean deleteMonHoc(@PathVariable Integer id) {
        return monHocService.deleteMonHoc(id);
    }

    // Render trang thêm môn học
    @GetMapping("/addMonHoc")
    public String showAddMonHocForm(Model model) {
        model.addAttribute("monHoc", new MonHocDTO());
        return "admin/monhoc/addMonHoc";
    }

    // Render trang sửa môn học
    @GetMapping("/editMonHoc/{id}")
    public String showEditMonHocForm(@PathVariable Integer id, Model model) {
        MonHocDTO monHoc = monHocService.getMonHocById(id);
        model.addAttribute("monHoc", monHoc);
        return "admin/monhoc/editMonHoc";
    }

    // Xử lý xóa môn học và redirect
    @GetMapping("/deleteMonHoc/{id}")
    public String deleteMonHocAndRedirect(@PathVariable Integer id) {
        monHocService.deleteMonHoc(id);
        return "redirect:/admin/monhoc";
    }
}