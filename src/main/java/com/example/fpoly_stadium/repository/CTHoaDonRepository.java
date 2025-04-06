package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.model.ChiTietHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CTHoaDonRepository extends JpaRepository<ChiTietHoaDon,Integer> {
    List<ChiTietHoaDon> findByHoaDonId(Integer hoaDonId);
}
