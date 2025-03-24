package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.entity.MonHocEntity;
import com.javaweb.student_score_management.repository.MonHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;

    public List<MonHocEntity> getAllMonHoc() {
        return monHocRepository.findAll();
    }
}