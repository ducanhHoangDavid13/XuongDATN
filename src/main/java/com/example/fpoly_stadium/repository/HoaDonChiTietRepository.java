package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.hoaDon.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> {
}
