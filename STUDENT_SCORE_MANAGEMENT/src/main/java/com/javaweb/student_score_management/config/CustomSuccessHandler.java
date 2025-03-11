package com.javaweb.student_score_management.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
//        System.out.println("DEBUG: Đăng nhập thành công! Gọi CustomSuccessHandler");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

//        System.out.println("DEBUG: Danh sách quyền của user:");
        authorities.forEach(auth -> System.out.println(" - " + auth.getAuthority()));

        String redirectUrl = "/";

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("Admin"))) {
            redirectUrl = "/admin/index";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("GiangVien"))) {
            redirectUrl = "/giangvien/index";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("SinhVien"))) {
            redirectUrl = "/sinhvien/index";
        }

        response.sendRedirect(redirectUrl);
    }
}

