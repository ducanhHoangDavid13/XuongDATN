package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.PhieuGiamGia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Optional;

public interface PhieuGiamGiaService {
    PhieuGiamGia createPhieuGiamGia(PhieuGiamGia phieu);
    PhieuGiamGia updatePhieuGiamGia(PhieuGiamGia phieu);
    void deletePhieuGiamGia(Integer id);
    Optional<PhieuGiamGia> getPhieuGiamGiaById(Integer id);
    List<PhieuGiamGia> getAllPhieuGiamGia();

    Optional<PhieuGiamGia> findByMaGiamGia(@NotBlank(message = "Mã giảm giá không được để trống") @Size(max = 50, message = "Mã giảm giá không vượt quá 50 ký tự") String maGiamGia);
}
