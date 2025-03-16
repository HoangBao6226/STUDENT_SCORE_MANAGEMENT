package com.javaweb.student_score_management.config;

import com.javaweb.student_score_management.service.implement.CustomUserDetailsSerImplement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsSerImplement customUserDetailsService;
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomUserDetailsSerImplement customUserDetailsService, CustomSuccessHandler customSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        //bỏ qua đăng nhập để test
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorize -> {
//                    try {
//                        authorize
//                                .requestMatchers("/**").permitAll() // 🔥 Bỏ qua xác thực cho tất cả request
//                                .anyRequest().permitAll();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                })
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                                .requestMatchers("/sinhvien/bangdiem").permitAll()
                                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/admin/index").hasAuthority("Admin")
                                .requestMatchers("/giangvien/index").hasAuthority("GiangVien")
                                .requestMatchers("/sinhvien/index").hasAuthority("SinhVien")
                                .anyRequest().authenticated();

//                        System.out.println("DEBUG: Cấu hình bảo mật đã tải thành công!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }
}
