package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.entity.SinhVienEntity;
import org.springframework.stereotype.Service;

@Service
public interface SinhVienService {
    SinhVienEntity addSinhVien(SinhVienEntity sinhVien);
    void deleteSinhVien(int maSV);
    SinhVienEntity findByMaSV(int maSV);
}
