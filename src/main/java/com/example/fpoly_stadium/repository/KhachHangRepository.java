package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<Account,Integer> {
    List<Account> findByLoaiTaiKhoan(String loaiTaiKhoan);
}
