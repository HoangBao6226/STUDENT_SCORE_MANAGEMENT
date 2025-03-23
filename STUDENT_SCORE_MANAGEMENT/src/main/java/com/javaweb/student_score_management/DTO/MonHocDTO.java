package com.javaweb.student_score_management.DTO;

public class MonHocDTO {
    private Integer maMH;
    private String tenMH;
    private Integer soTinChi;
    private Integer maGV;

    public MonHocDTO() {
    }

    public MonHocDTO(Integer maMH, String tenMH, Integer soTinChi, Integer maGV) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.soTinChi = soTinChi;
        this.maGV = maGV;
    }

    public Integer getMaMH() {
        return maMH;
    }

    public void setMaMH(Integer maMH) {
        this.maMH = maMH;
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

    public Integer getMaGV() {
        return maGV;
    }

    public void setMaGV(Integer maGV) {
        this.maGV = maGV;
    }


}
