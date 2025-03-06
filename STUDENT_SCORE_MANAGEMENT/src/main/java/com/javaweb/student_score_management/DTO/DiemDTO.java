package com.javaweb.student_score_management.DTO;

public class DiemDTO {
    private Integer maDiem;
    private String tenGV;
    private String tenMH;
    private Integer soTinChi;
    private Float diem;

    public DiemDTO(Integer maDiem, String tenGV, String tenMH, Integer soTinChi, Float diem) {
        this.maDiem = maDiem;
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
}
