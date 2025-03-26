package com.javaweb.student_score_management.DTO;

public class DiemDTO {
    private Integer maDiem;
    private Integer maSV;
    private Integer maMH;
    private String tenSV;
    private String tenGV;
    private String tenMH;
    private Integer soTinChi;
    private Float diem;

    public DiemDTO(Integer maDiem, Integer maSV, Integer maMH, String tenSV, String tenGV, String tenMH, Integer soTinChi, Float diem) {
        this.maDiem = maDiem;
        this.maSV = maSV;
        this.maMH = maMH;
        this.tenSV = tenSV;
        this.tenGV = tenGV;
        this.tenMH = tenMH;
        this.soTinChi = soTinChi;
        this.diem = diem;
    }

    // Getter và Setter
    public Integer getMaDiem() {
        return maDiem;
    }

    public void setMaDiem(Integer maDiem) {
        this.maDiem = maDiem;
    }

    public String getTenGV() {
        return tenGV;
    }

    public void setTenGV(String tenGV) {
        this.tenGV = tenGV;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public Integer getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(Integer soTinChi) {
        this.soTinChi = soTinChi;
    }

    public Float getDiem() {
        return diem;
    }

    public void setDiem(Float diem) {
        this.diem = diem;
    }

    public Integer getMaSV() {
        return maSV;
    }

    public void setMaSV(Integer maSV) {
        this.maSV = maSV;
    }

    public Integer getMaMH() {
        return maMH;
    }

    public void setMaMH(Integer maMH) {
        this.maMH = maMH;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }
}
