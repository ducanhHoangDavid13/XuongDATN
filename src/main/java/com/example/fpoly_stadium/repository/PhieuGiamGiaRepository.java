package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.hoaDon.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, Integer> {
}
