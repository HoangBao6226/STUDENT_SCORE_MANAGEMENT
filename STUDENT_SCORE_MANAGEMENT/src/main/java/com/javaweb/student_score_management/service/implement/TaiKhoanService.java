package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.DTO.TaiKhoanDTO;
import com.javaweb.student_score_management.entity.GiangVienEntity;
import com.javaweb.student_score_management.entity.SinhVienEntity;
import com.javaweb.student_score_management.entity.TaiKhoanEntity;
import com.javaweb.student_score_management.repository.GiangVienRepository;
import com.javaweb.student_score_management.repository.SinhVienRepository;
import com.javaweb.student_score_management.repository.TaiKhoanRepository;
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
    private TaiKhoanRepository taiKhoanRepository;

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
            if (taiKhoanRepository.existsById(id)) {
                taiKhoanRepository.deleteById(id);
                logger.info("Xóa tài khoản thành công với ID: {}", id);
                return true;
            } else {
                logger.error("Không tìm thấy tài khoản với ID: {}", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("Lỗi khi xóa tài khoản: ", e);
            return false;
        }
    }
}