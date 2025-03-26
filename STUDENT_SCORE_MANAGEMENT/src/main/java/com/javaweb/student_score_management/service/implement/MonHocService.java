package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.DTO.MonHocDTO;
import com.javaweb.student_score_management.entity.GiangVienEntity;
import com.javaweb.student_score_management.entity.MonHocEntity;
import com.javaweb.student_score_management.repository.GiangVienRepository;
import com.javaweb.student_score_management.repository.MonHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private GiangVienRepository giangVienRepository;

    public List<MonHocDTO> getAllMonHoc() {
        return monHocRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public MonHocDTO getMonHocById(Integer id) {
        return monHocRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public boolean createMonHoc(MonHocDTO monHocDTO) {
        try {
            MonHocEntity monHoc = new MonHocEntity();
            monHoc.setTenMH(monHocDTO.getTenMH());
            monHoc.setSoTinChi(monHocDTO.getSoTinChi());
            monHocRepository.save(monHoc);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateMonHoc(Integer id, MonHocDTO monHocDTO) {
        return monHocRepository.findById(id).map(monHoc -> {
            monHoc.setTenMH(monHocDTO.getTenMH());
            monHoc.setSoTinChi(monHocDTO.getSoTinChi());
            monHocRepository.save(monHoc);
            return true;
        }).orElse(false);
    }

    public boolean deleteMonHoc(Integer id) {
        if (monHocRepository.existsById(id)) {
            monHocRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private MonHocDTO convertToDTO(MonHocEntity monHoc) {
        return new MonHocDTO(
                monHoc.getMaMH(),
                monHoc.getTenMH(),
                monHoc.getSoTinChi(),
                monHoc.getMaGV() != null ? monHoc.getMaGV().getMaGV() : null
        );
    }

    //GV lấy DSMH mình dạy
    public List<MonHocDTO> getMonHocByMaGV(int maGV) {

        GiangVienEntity giangVienEntity = giangVienRepository.findById(maGV).get();

        List<MonHocEntity> listMH = monHocRepository.findByMaGV(giangVienEntity);

        List<MonHocDTO> monHocDTOList = new ArrayList<>();

        for(MonHocEntity monHocEntity : listMH){
            MonHocDTO monHocDTO = convertToDTO(monHocEntity);
            monHocDTOList.add(monHocDTO);
        }

        return monHocDTOList;

    }
}
