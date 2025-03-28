package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.entity.SinhVienEntity;
import com.javaweb.student_score_management.entity.TaiKhoanEntity;
import com.javaweb.student_score_management.repository.DiemRepository;
import com.javaweb.student_score_management.repository.SinhVienRepository;
import com.javaweb.student_score_management.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private DiemRepository diemRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Override
    public SinhVienEntity addSinhVien(SinhVienEntity sinhVien) {
        // Check if maSV already exists
        if (sinhVienRepository.findById(sinhVien.getMaSV()).isPresent()) {
            throw new RuntimeException("Mã sinh viên đã tồn tại!");
        }
        return sinhVienRepository.save(sinhVien);
    }

    @Override
    public void deleteSinhVien(int maSV) {
        SinhVienEntity sinhVien = sinhVienRepository.findById(maSV).orElse(null);
        if (sinhVien == null) {
            throw new RuntimeException("Sinh viên không tồn tại!");
        }

        // Delete related DiemEntity records
        List<DiemEntity> diemList = sinhVien.getListDiem();
        if (diemList != null && !diemList.isEmpty()) {
            diemRepository.deleteAll(diemList);
        }

        // Delete related TaiKhoanEntity records
        List<TaiKhoanEntity> taiKhoanList = sinhVien.getListTaiKhoan();
        if (taiKhoanList != null && !taiKhoanList.isEmpty()) {
            taiKhoanRepository.deleteAll(taiKhoanList);
        }

        // Delete the SinhVienEntity
        sinhVienRepository.deleteById(maSV);
    }

    @Override
    public SinhVienEntity findByMaSV(int maSV) {
        return sinhVienRepository.findById(maSV).orElse(null);
    }
}