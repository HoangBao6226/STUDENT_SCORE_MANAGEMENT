package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.DTO.TaiKhoanDTO;
import com.javaweb.student_score_management.entity.*;
import com.javaweb.student_score_management.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaiKhoanService {
    private static final Logger logger = LoggerFactory.getLogger(TaiKhoanService.class);

    @Autowired
    private GiangVienRepository giangVienRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private DiemRepository diemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TaiKhoanEntity findByUsername(String username) {
        return taiKhoanRepository.findByUsername(username).orElse(null);
    }

    public List<TaiKhoanEntity> getAllTaiKhoan() {
        return taiKhoanRepository.findAll();
    }

    public Boolean create(TaiKhoanDTO taiKhoan) {
        try {
            if (taiKhoanRepository.existsByUsername(taiKhoan.getUsername())) {
                logger.error("Username đã tồn tại: {}", taiKhoan.getUsername());
                return false;
            }


            TaiKhoanEntity taiKhoanEntity = new TaiKhoanEntity();
            if (taiKhoan.getAccount_type().equals("GiangVien")) {
                if (giangVienRepository.existsByEmail(taiKhoan.getEmail())) {
                    logger.error("Email đã tồn tại trong GiangVien: {}", taiKhoan.getEmail());
                    return false;
                }
                GiangVienEntity giangVienEntity = new GiangVienEntity();
                giangVienEntity.setEmail(taiKhoan.getEmail());
                giangVienEntity.setTenGV(taiKhoan.getName());
                giangVienRepository.save(giangVienEntity);
                taiKhoanEntity.setMaGV(giangVienRepository.findById(giangVienEntity.getMaGV()).get());
                taiKhoanEntity.setRole(TaiKhoanEntity.Role.GiangVien);
                taiKhoanEntity.setUsername(taiKhoan.getUsername());
                taiKhoanEntity.setPassword(passwordEncoder.encode(taiKhoan.getPassword()));
//            taiKhoan.setPassword(passwordEncoder.encode(taiKhoan.getPassword()));
                taiKhoanRepository.save(taiKhoanEntity);

                logger.info("Tạo tài khoản thành công: {}", taiKhoan.getUsername());
                return true;
            } else{
                if (sinhVienRepository.existsByEmail(taiKhoan.getEmail())) {
                    logger.error("Email đã tồn tại trong SinhVien: {}", taiKhoan.getEmail());
                    return false;
                }
                taiKhoanEntity.setRole(TaiKhoanEntity.Role.SinhVien);
                SinhVienEntity sinhVienEntity = new SinhVienEntity();
                sinhVienEntity.setEmail(taiKhoan.getEmail());
                sinhVienEntity.setTenSV(taiKhoan.getName());
                sinhVienRepository.save(sinhVienEntity);
                taiKhoanEntity.setMaSV(sinhVienRepository.findById(sinhVienEntity.getMaSV()).get());
                taiKhoanEntity.setUsername(taiKhoan.getUsername());
                taiKhoanEntity.setPassword(passwordEncoder.encode(taiKhoan.getPassword()));
//            taiKhoan.setPassword(passwordEncoder.encode(taiKhoan.getPassword()));
                taiKhoanRepository.save(taiKhoanEntity);

                logger.info("Tạo tài khoản thành công: {}", taiKhoan.getUsername());
                return true;
            }


//            taiKhoanEntity.setUsername(taiKhoan.getUsername());
//            taiKhoanEntity.setPassword(passwordEncoder.encode(taiKhoan.getPassword()));
////            taiKhoan.setPassword(passwordEncoder.encode(taiKhoan.getPassword()));
//            taiKhoanRepository.save(taiKhoanEntity);
//
//            logger.info("Tạo tài khoản thành công: {}", taiKhoan.getUsername());
//            return true;
        } catch (Exception e) {
            logger.error("Lỗi khi tạo tài khoản: ", e);
            return false;
        }
    }

    public TaiKhoanEntity findById(Integer id) {
        return taiKhoanRepository.findById(id).orElse(null);
    }

    public Boolean update(TaiKhoanDTO taiKhoan) {
        try {
            Optional<TaiKhoanEntity> optionalTaiKhoan = taiKhoanRepository.findById(taiKhoan.getMaTK());
            if (optionalTaiKhoan.isPresent()) {
                TaiKhoanEntity taiKhoanEntity = optionalTaiKhoan.get();

                // Kiểm tra nếu tài khoản có mã giảng viên
                if (taiKhoanEntity.getMaGV() == null) {
                    logger.error("Không thể cập nhật vì tài khoản không có mã giảng viên: ID {}", taiKhoan.getMaTK());
                    return false;
                }

                // Cập nhật thông tin tài khoản
                taiKhoanEntity.setUsername(taiKhoan.getUsername());
                if (taiKhoan.getPassword() != null && !taiKhoan.getPassword().isEmpty()) {
                    taiKhoanEntity.setPassword(passwordEncoder.encode(taiKhoan.getPassword()));
                }
                taiKhoanRepository.save(taiKhoanEntity);

                logger.info("Cập nhật tài khoản thành công: {}", taiKhoan.getUsername());
                return true;
            } else {
                logger.error("Không tìm thấy tài khoản với ID: {}", taiKhoan.getMaTK());
                return false;
            }
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật tài khoản", e);
            return false;
        }
    }


    public Boolean delete(Integer id) {
        try {
            if (!taiKhoanRepository.existsById(id)) {
                logger.error("Không tìm thấy tài khoản với ID: {}", id);
                return false;
            }

            Optional<TaiKhoanEntity> taiKhoanOpt = taiKhoanRepository.findById(id);
            if (taiKhoanOpt.isPresent()) {
                TaiKhoanEntity taiKhoan = taiKhoanOpt.get();

                // Kiểm tra nếu tài khoản có liên kết với giảng viên
                if (taiKhoan.getMaGV() != null) {
                    GiangVienEntity giangVien = taiKhoan.getMaGV();
                    List<MonHocEntity> danhSachMonHoc = monHocRepository.findByMaGV(giangVien);

                    if (!danhSachMonHoc.isEmpty()) {
                        logger.error("Không thể xóa giảng viên ID: {} vì còn môn học đang giảng dạy", id);
                        return false;
                    }

                    // Xóa giảng viên sau khi xóa tài khoản
                    taiKhoanRepository.deleteById(id);
                    giangVienRepository.delete(giangVien);
                    logger.info("Xóa tài khoản và giảng viên thành công với ID: {}", id);
                    return true;
                }

                // Kiểm tra nếu tài khoản có liên kết với sinh viên
                if (taiKhoan.getMaSV() != null) {
                    SinhVienEntity sinhVien = taiKhoan.getMaSV();
                    List<DiemEntity> danhSachDiem = diemRepository.findByMaSV(sinhVien);

                    if (!danhSachDiem.isEmpty()) {
                        logger.error("Không thể xóa sinh viên ID: {} vì đã có điểm môn học", id);
                        return false;
                    }

                    // Xóa sinh viên sau khi xóa tài khoản
                    taiKhoanRepository.deleteById(id);
                    sinhVienRepository.delete(sinhVien);
                    logger.info("Xóa tài khoản và sinh viên thành công với ID: {}", id);
                    return true;
                }

                // Nếu không phải giảng viên hay sinh viên, chỉ cần xóa tài khoản
                taiKhoanRepository.deleteById(id);
                logger.info("Xóa tài khoản thành công với ID: {}", id);
                return true;
            }

            return false;
        } catch (Exception e) {
            logger.error("Lỗi khi xóa tài khoản: ", e);
            return false;
        }
    }

}