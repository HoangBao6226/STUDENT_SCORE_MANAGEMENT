package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.entity.MonHocEntity;
import com.javaweb.student_score_management.entity.SinhVienEntity;
import com.javaweb.student_score_management.repository.DiemRepository;
import com.javaweb.student_score_management.repository.MonHocRepository;
import com.javaweb.student_score_management.repository.SinhVienRepository;
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
    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private MonHocRepository monHocRepository;

    // Đăng ký môn học mới (luôn thêm bản ghi mới)
    public String dangKyMonHoc(Integer maSV, Integer maMH) {
        SinhVienEntity sinhVien = sinhVienRepository.findById(maSV).orElse(null);
        MonHocEntity monHoc = monHocRepository.findById(maMH).orElse(null);

        if (sinhVien == null || monHoc == null) {
            return "Sinh viên hoặc môn học không tồn tại!";
        }

        DiemEntity diemMoi = new DiemEntity();
        diemMoi.setMaSV(sinhVien);
        diemMoi.setMaMH(monHoc);
        diemMoi.setDiem(null); // Chưa có điểm

        diemRepository.save(diemMoi);
        return "Đăng ký môn học thành công!";
    }

    // Xóa môn học (chỉ xóa nếu chưa có điểm)
    public void xoaMonHoc(Integer maSV, Integer maMH) {
        SinhVienEntity sv = sinhVienRepository.findById(maSV)
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại!"));

        MonHocEntity mh = monHocRepository.findById(maMH)
                .orElseThrow(() -> new RuntimeException("Môn học không tồn tại!"));

        // Tìm điểm dựa trên sinh viên và môn học
        DiemEntity diem = diemRepository.findByMaSVAndMaMH(sv, mh)
                .orElseThrow(() -> new RuntimeException("Sinh viên chưa đăng ký môn học này!"));

        if (diem.getDiem() != null) {
            throw new RuntimeException("Không thể xóa môn học đã có điểm!");
        }

        diemRepository.delete(diem);
    }
}