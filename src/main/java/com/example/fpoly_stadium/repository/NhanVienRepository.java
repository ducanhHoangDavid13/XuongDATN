package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.user.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    List<NhanVien> findByTrangThai(Integer trangThai);
}
