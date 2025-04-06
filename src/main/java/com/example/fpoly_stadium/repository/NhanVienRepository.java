package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<Account,Integer> {
    List<Account> findByLoaiTaiKhoan(String loaiTaiKhoan);
}
