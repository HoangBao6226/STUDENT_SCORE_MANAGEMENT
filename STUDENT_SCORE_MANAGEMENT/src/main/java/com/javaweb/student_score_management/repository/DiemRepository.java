package com.javaweb.student_score_management.repository;

import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.entity.SinhVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
//@Repository
//public interface DiemRepository extends JpaRepository<DiemEntity, Integer> {
//    List<DiemEntity> findByMaSV(SinhVienEntity maSV);
//}
@Repository
public interface DiemRepository extends JpaRepository<DiemEntity, Integer> {
    @Query("SELECT d FROM DiemEntity d " +
            "JOIN FETCH d.maSV sv " +
            "JOIN FETCH d.maMH mh " +
            "JOIN FETCH mh.maGV gv " +
            "WHERE sv.maSV = :maSV")
    List<DiemEntity> getDiemDetailsByMaSV(@Param("maSV") Integer maSV);
}