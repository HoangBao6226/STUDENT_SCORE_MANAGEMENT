package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.entity.TaiKhoanEntity;
import com.javaweb.student_score_management.repository.TaiKhoanRepository;
import com.javaweb.student_score_management.service.implement.TaiKhoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    //Trang chủ là index
    @GetMapping("/index")
    public String adminIndex() {
        return "admin/index";
    }

    //CRUD Taif Khoản
    private final TaiKhoanRepository taiKhoanRepository;

    public AdminController(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @GetMapping("/index/listTaiKhoan")
    public String listAccounts(Model model) {
        List<TaiKhoanEntity> list = taiKhoanRepository.findAll();
        model.addAttribute("list", list);
        return "admin/index/listTaiKhoan/index"; //Trỏ tới thư mục của FE để xem danh sách tài khoản
    }


    @GetMapping("/addTaiKhoan")
    public String showAddForm(Model model) {
        model.addAttribute("dstk", new TaiKhoanEntity());
        return "admin/index/listTaiKhoan/add-TaiKhoan";
    }

    @PostMapping("/addTaiKhoan")
    public String addAccount(@ModelAttribute TaiKhoanEntity taiKhoan) {
        taiKhoanRepository.save(taiKhoan);
        return "redirect:/admin/index/listTaiKhoan/index";
    }

    @GetMapping("/editTaiKhoan/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        TaiKhoanEntity taiKhoan = taiKhoanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid account ID:" + id));
        model.addAttribute("dstk", taiKhoan);
        return "admin/index/listTaiKhoan/edit-TaiKhoan";
    }

    @PostMapping("/editTaiKhoan/{id}")
    public String updateAccount(@PathVariable Integer id, @ModelAttribute TaiKhoanEntity taiKhoan) {
        taiKhoan.setMaTK(id); // Đảm bảo cập nhật đúng ID
        taiKhoanRepository.save(taiKhoan);
        return "redirect:/admin/index/listTaiKhoan/index";
    }

    @GetMapping("/account/delete/{id}")
    public String deleteAccount(@PathVariable Integer id) {
        taiKhoanRepository.deleteById(id);
        return "redirect:/admin/index/listTaiKhoan/index";
    }
}
