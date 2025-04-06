package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.Account;
import com.example.fpoly_stadium.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired // Inject Repository
    private AccountRepository accountRepository;

    @Override // Ghi đè phương thức của UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tìm Account trong DB bằng username
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy tài khoản với username: " + username));

        // 2. Kiểm tra trạng thái tài khoản
        boolean enabled = "HoatDong".equals(account.getTrangThai());
        boolean accountNonLocked = !"BiKhoa".equals(account.getTrangThai());
        // Các trạng thái khác (nonExpired) thường để true trừ khi có logic cụ thể
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;

        // 3. Tạo quyền hạn từ loai_tai_khoan
        // Sử dụng tên loại tài khoản trực tiếp làm Authority
        GrantedAuthority authority = new SimpleGrantedAuthority(account.getLoaiTaiKhoan());
        Collection<GrantedAuthority> authorities = Collections.singletonList(authority);

        // 4. Tạo và trả về đối tượng UserDetails
        return new User(account.getUsername(), // Username
                account.getPasswordHash(), // Password đã hash
                enabled, // Tài khoản có được kích hoạt?
                accountNonExpired, // Tài khoản có hết hạn?
                credentialsNonExpired, // Mật khẩu có hết hạn?
                accountNonLocked, // Tài khoản có bị khóa?
                authorities); // Danh sách quyền hạn
    }
}
