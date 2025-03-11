package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.entity.AdminEntity;
import com.javaweb.student_score_management.entity.TaiKhoanEntity;
import com.javaweb.student_score_management.repository.AdminRepository;
import com.javaweb.student_score_management.repository.TaiKhoanRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaiKhoanService {
    private final TaiKhoanRepository taiKhoanRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public TaiKhoanService(TaiKhoanRepository taiKhoanRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<TaiKhoanEntity> getAllTaiKhoan(Integer maAdmin) {
        return taiKhoanRepository.findByMaAdmin_MaAdmin(maAdmin);
    }

    public Optional<TaiKhoanEntity> getTaiKhoanById(Integer id) {
        return taiKhoanRepository.findById(id);
    }

    public TaiKhoanEntity createTaiKhoan(Integer maAdmin, TaiKhoanEntity taiKhoan) {
        Optional<AdminEntity> admin = adminRepository.findById(maAdmin);
        if (admin.isPresent()) {
            taiKhoan.setMaAdmin(admin.get());
            taiKhoan.setPassword(passwordEncoder.encode(taiKhoan.getPassword())); // Mã hóa mật khẩu
            return taiKhoanRepository.save(taiKhoan);
        } else {
            throw new RuntimeException("Admin không tồn tại");
        }
    }

    public TaiKhoanEntity updateTaiKhoan(Integer id, TaiKhoanEntity updatedTaiKhoan) {
        return taiKhoanRepository.findById(id).map(taiKhoan -> {
            taiKhoan.setUsername(updatedTaiKhoan.getUsername());
            if (!updatedTaiKhoan.getPassword().isEmpty()) {
                taiKhoan.setPassword(passwordEncoder.encode(updatedTaiKhoan.getPassword()));
            }
            taiKhoan.setRole(updatedTaiKhoan.getRole());
            return taiKhoanRepository.save(taiKhoan);
        }).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
    }

    public void deleteTaiKhoan(Integer id) {
        taiKhoanRepository.deleteById(id);
    }



}
