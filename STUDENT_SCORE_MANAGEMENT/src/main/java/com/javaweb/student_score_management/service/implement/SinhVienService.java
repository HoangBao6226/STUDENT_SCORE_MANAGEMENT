package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.entity.SinhVienEntity;
import com.javaweb.student_score_management.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;

    public Optional<SinhVienEntity> getSinhVienById(Integer maSV) {
        return sinhVienRepository.findById(maSV);
    }
}