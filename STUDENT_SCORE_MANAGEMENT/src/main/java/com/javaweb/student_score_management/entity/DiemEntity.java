package com.javaweb.student_score_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "diem")
public class DiemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maDiem;

    @ManyToOne
    @JoinColumn(name = "maSV")
    private SinhVienEntity maSV;

    @ManyToOne
    @JoinColumn(name = "maMH")
    private MonHocEntity maMH;

    @Column(name = "diem")
    private Float diem;

    public Integer getMaDiem() {
        return maDiem;
    }

    public void setMaDiem(Integer maDiem) {
        this.maDiem = maDiem;
    }

    public SinhVienEntity getMaSV() {
        return maSV;
    }

    public void setMaSV(SinhVienEntity maSV) {
        this.maSV = maSV;
    }

    public MonHocEntity getMaMH() {
        return maMH;
    }

    public void setMaMH(MonHocEntity maMH) {
        this.maMH = maMH;
    }

    public Float getDiem() {
        return diem;
    }

    public void setDiem(Float diem) {
        this.diem = diem;
    }
}
