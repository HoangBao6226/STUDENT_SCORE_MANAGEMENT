package com.javaweb.student_score_management.controller;

import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.service.implement.DiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@RestController
//public class DiemController {
//    @Autowired
//    private DiemService diemService;
//
//    @GetMapping("/sinhvien/bangdiem")
//    public ResponseEntity<?> xemBangDiem(@RequestParam("maSV") Integer maSV) {
//        try {
//            List<DiemEntity> diemList = diemService.getDiembySinhVienID(maSV);
//            return ResponseEntity.ok(diemList);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
//}
@RestController
@RequestMapping("/sinhvien")
public class DiemController {
    @Autowired
    private DiemService diemService;

//    @GetMapping("/bangdiem")
//    public String xemBangDiem(@RequestParam("maSV") Integer maSV, Model model) {
//        List<DiemEntity> diemList = diemService.getDiemDetailsBySinhVienID(maSV);
//        model.addAttribute("diemList", diemList);
//        return "bangdiem";
//    }

    //json
    @GetMapping("/bangdiem")
    public ResponseEntity<List<DiemEntity>> xemBangDiem(@RequestParam("maSV") Integer maSV) {
        List<DiemEntity> diemList = diemService.getDiemDetailsBySinhVienID(maSV);
        return ResponseEntity.ok(diemList);
    }

}