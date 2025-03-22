package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.entity.MonHocEntity;
import com.javaweb.student_score_management.entity.SinhVienEntity;
import com.javaweb.student_score_management.repository.DiemRepository;
import com.javaweb.student_score_management.repository.MonHocRepository;
import com.javaweb.student_score_management.repository.SinhVienRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

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
    private static final Logger logger = LoggerFactory.getLogger(DiemService.class);

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
    //Admin
    public List<DiemDTO> getAllDiem() {
        List<DiemEntity> diemEntities = diemRepository.findAll();
        return diemEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public boolean createDiem(DiemDTO diemDTO) {
        try {
            SinhVienEntity sinhVien = sinhVienRepository.findById(diemDTO.getMaSV())
                    .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại!"));
            MonHocEntity monHoc = monHocRepository.findById(diemDTO.getMaMH())
                    .orElseThrow(() -> new RuntimeException("Môn học không tồn tại!"));

            DiemEntity diemEntity = new DiemEntity();
            diemEntity.setMaSV(sinhVien);
            diemEntity.setMaMH(monHoc);
            diemEntity.setDiem(diemDTO.getDiem());

            diemRepository.save(diemEntity);
            return true;
        } catch (Exception e) {
            logger.error("Lỗi khi thêm điểm", e);
            return false;
        }
    }

    public boolean updateDiem(Integer id, DiemDTO diemDTO) {
        try {
            DiemEntity diemEntity = diemRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Điểm không tồn tại!"));

            diemEntity.setDiem(diemDTO.getDiem());
            diemRepository.save(diemEntity);
            return true;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật điểm", e);
            return false;
        }
    }

    public boolean deleteDiem(Integer id) {
        try {
            if (diemRepository.existsById(id)) {
                diemRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Lỗi khi xóa điểm", e);
            return false;
        }
    }

    //FE sẽ hiển thị các trường (Field) thông tin trên VIEW danh sách điểm là:
    //maDiem → Mã điểm
    //maSV → Mã sinh viên
    //maMH → Mã môn học
    //tenGV → Tên giảng viên
    //tenMH → Tên môn học
    //soTinChi → Số tín chỉ
    //diem → Điểm số
    //DTO có chức năng là sao chép các trường thông tin bên ENTITY để chỉ hiện những thông tin cần thiết/cho phép để hiển thị
    private DiemDTO convertToDTO(DiemEntity diemEntity) {
        return new DiemDTO(
                diemEntity.getMaDiem(),
                diemEntity.getMaSV().getMaSV(),
                diemEntity.getMaMH().getMaMH(),
                diemEntity.getMaMH().getMaGV().getTenGV(),
                diemEntity.getMaMH().getTenMH(),
                diemEntity.getMaMH().getSoTinChi(),
                diemEntity.getDiem()
        );
    }

}