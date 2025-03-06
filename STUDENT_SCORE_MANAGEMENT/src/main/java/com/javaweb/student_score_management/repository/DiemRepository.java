package com.javaweb.student_score_management.repository;

import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.entity.SinhVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DiemRepository extends JpaRepository<DiemEntity, Integer> {
    List<DiemEntity> findByMaSV(SinhVienEntity maSV);
}
