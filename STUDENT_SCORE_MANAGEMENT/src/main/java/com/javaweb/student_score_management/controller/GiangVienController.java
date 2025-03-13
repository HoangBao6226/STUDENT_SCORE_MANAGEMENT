package com.javaweb.student_score_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/giangvien")
public class GiangVienController {

    //Trang chủ là index
    @GetMapping("/index")
    public String giangVienIndex() {
        return "giangvien/index";
    }
}
