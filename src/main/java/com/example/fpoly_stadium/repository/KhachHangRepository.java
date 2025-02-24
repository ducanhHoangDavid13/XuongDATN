package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.user.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
}
