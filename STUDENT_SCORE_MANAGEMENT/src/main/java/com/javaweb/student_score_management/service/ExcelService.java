package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.DTO.DiemDTO;
import com.javaweb.student_score_management.entity.DiemEntity;
import com.javaweb.student_score_management.entity.MonHocEntity;
import com.javaweb.student_score_management.entity.SinhVienEntity;
import com.javaweb.student_score_management.repository.DiemRepository;
import com.javaweb.student_score_management.repository.MonHocRepository;
import com.javaweb.student_score_management.repository.SinhVienRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private DiemRepository diemRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private DiemService diemService;

    public List<DiemDTO> importExcel(MultipartFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

        List<DiemDTO> diemDTOList = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Bỏ qua dòng tiêu đề

            SinhVienEntity sv = sinhVienRepository.findById((int) row.getCell(0).getNumericCellValue()).get();
            MonHocEntity mh = monHocRepository.findById((int) row.getCell(1).getNumericCellValue()).get();
            DiemEntity diem = new DiemEntity();
            diem.setMaSV(sv);
            diem.setMaMH(mh);
            diem.setDiem((float) row.getCell(2).getNumericCellValue());

            diemRepository.save(diem);

            DiemDTO diemDTO = diemService.convertToDTO(diem);
            diemDTOList.add(diemDTO);
        }
        workbook.close();

        return diemDTOList;

    }
}

