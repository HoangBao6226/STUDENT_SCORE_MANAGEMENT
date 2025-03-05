package com.javaweb.student_score_management.service.implement;

import com.javaweb.student_score_management.entity.TaiKhoanEntity;
import com.javaweb.student_score_management.service.intface.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsSerImplement implements UserDetailsService {
    private final AccountRepository accountRepository;


    public CustomUserDetailsSerImplement(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TaiKhoanEntity account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(account.getRole().name()));

        return new User(
                account.getUsername(),
                account.getPassword(),
                authorities
        );
    }
}
