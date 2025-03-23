package com.javaweb.student_score_management.DTO;

public class TaiKhoanDTO {
    private String account_type;
    private String username;
    private String password;
    private String email;
    private String name;

    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    private int maTK;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public TaiKhoanDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }


}
