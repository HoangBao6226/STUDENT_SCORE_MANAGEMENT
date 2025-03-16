package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.repository.DiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class DiemService {
//    @Autowired
//    private DiemRepository diemRepository;
//
//    @Autowired
//    private SinhVienRepository sinhVienRepository;
//
//    public List<DiemEntity> getDiembySinhVienID(Integer maSV) {
//        SinhVienEntity sv = sinhVienRepository.findById(maSV).orElse(null);
//        if (sv == null) {
//            throw new RuntimeException("Sinh viên không tồn tại!");
//        }
//
//        List<DiemEntity> danhSachDiem = diemRepository.findByMaSV(sv);
//        System.out.println("Danh sách điểm lấy được: " + danhSachDiem);
//        return danhSachDiem;
//    }
//}
@Service
public class DiemService {
    @Autowired
    private DiemRepository diemRepository;

    public List<DiemEntity> getDiemDetailsBySinhVienID(Integer maSV) {
        return diemRepository.getDiemDetailsByMaSV(maSV);
    }
}