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
    @Query("SELECT new com.javaweb.student_score_management.DTO.DiemDTO(d.maDiem, gv.tenGV, mh.tenMH, mh.soTinChi, d.diem) " +
            "FROM DiemEntity d " +
            "JOIN d.maSV sv " +
            "JOIN d.maMH mh " +
            "JOIN mh.maGV gv " +
            "WHERE sv.maSV = :maSV")
    List<DiemDTO> getDiemDetailsByMaSV(@Param("maSV") Integer maSV);


}