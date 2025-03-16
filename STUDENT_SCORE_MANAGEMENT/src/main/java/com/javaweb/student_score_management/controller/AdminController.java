package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.entity.TaiKhoanEntity;
import com.javaweb.student_score_management.repository.TaiKhoanRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TaiKhoanRepository taiKhoanRepository;

    public AdminController(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    // Khi truy cập /admin sẽ hiển thị trực tiếp trang index
    @GetMapping("")
    public String adminIndexShortcut() {
        return "admin/index";
    }

    // Trang /admin/index cũng hiển thị trang index.html
    @GetMapping("/index")
    public String adminIndex() {
        return "admin/index";
    }

    // Hiển thị danh sách tài khoản
    @GetMapping("/index/listTaiKhoan")
    public String listAccounts(Model model) {
        List<TaiKhoanEntity> list = taiKhoanRepository.findAll();
        model.addAttribute("list", list);
        return "admin/index/listTaiKhoan/index";
    }

    // Form thêm tài khoản
    @GetMapping("/addTaiKhoan")
    public String showAddForm(Model model) {
        model.addAttribute("dstk", new TaiKhoanEntity());
        return "admin/index/listTaiKhoan/add-TaiKhoan";
    }

    // Xử lý thêm tài khoản
    @PostMapping("/addTaiKhoan")
    public String addAccount(@ModelAttribute TaiKhoanEntity taiKhoan) {
        taiKhoanRepository.save(taiKhoan);
        return "redirect:/admin/index/listTaiKhoan/index";
    }

    // Form chỉnh sửa tài khoản
    @GetMapping("/editTaiKhoan/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        TaiKhoanEntity taiKhoan = taiKhoanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account ID: " + id));
        model.addAttribute("dstk", taiKhoan);
        return "admin/index/listTaiKhoan/edit-TaiKhoan";
    }

    // Xử lý cập nhật tài khoản
    @PostMapping("/editTaiKhoan/{id}")
    public String updateAccount(@PathVariable Integer id, @ModelAttribute TaiKhoanEntity taiKhoan) {
        taiKhoan.setMaTK(id); // Đảm bảo cập nhật đúng ID
        taiKhoanRepository.save(taiKhoan);
        return "redirect:/admin/index/listTaiKhoan/index";
    }

    // Xóa tài khoản
    @GetMapping("/account/delete/{id}")
    public String deleteAccount(@PathVariable Integer id) {
        taiKhoanRepository.deleteById(id);
        return "redirect:/admin/index/listTaiKhoan/index";
    }
}
