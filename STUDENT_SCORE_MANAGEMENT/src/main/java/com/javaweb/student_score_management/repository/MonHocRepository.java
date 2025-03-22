package com.javaweb.student_score_management.repository;

import com.javaweb.student_score_management.entity.MonHocEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonHocRepository extends JpaRepository<MonHocEntity, Integer> {
}
