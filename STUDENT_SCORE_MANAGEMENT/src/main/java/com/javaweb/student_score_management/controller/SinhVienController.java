package com.javaweb.student_score_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.entity.GiangVienEntity;
import com.javaweb.student_score_management.entity.MonHocEntity;
import com.javaweb.student_score_management.entity.SinhVienEntity;
import com.javaweb.student_score_management.repository.SinhVienRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sinhvien")
public class SinhVienController {

    @Autowired
    private SinhVienRepository sinhVienRepository;


    @GetMapping("/index")
    public String sinhVienindex() {
        return "sinhvien/index";
    }

    @GetMapping("/{maSV}")
    public void getDiemBySinhVien(@PathVariable Integer maSV, HttpServletResponse response) throws IOException {
        SinhVienEntity sinhVien = sinhVienRepository.findById(maSV).orElse(null);
        if (sinhVien == null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Sinh viên không tồn tại!");
            return;
        }

        List<DiemDTO> diemDTOList = sinhVien.getListDiem().stream().map(diem -> {
            MonHocEntity monHoc = diem.getMaMH();
            GiangVienEntity giangVien = monHoc.getMaGV();

            return new DiemDTO(
                    diem.getMaDiem(),
                    sinhVien.getMaSV(),
                    monHoc.getMaMH(),
                    giangVien != null ? giangVien.getTenGV() : "Chưa có",
                    monHoc.getTenMH(),
                    monHoc.getSoTinChi(),
                    diem.getDiem()
            );
        }).toList();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new ObjectMapper().writeValueAsString(diemDTOList));
    }

}