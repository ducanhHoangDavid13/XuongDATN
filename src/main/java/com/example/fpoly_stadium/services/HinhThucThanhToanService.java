package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.HinhThucThanhToan;

import java.util.List;

public interface HinhThucThanhToanService {
    HinhThucThanhToan createHinhThucThanhToan(HinhThucThanhToan httt);
    HinhThucThanhToan updateHinhThucThanhToan(HinhThucThanhToan httt);
    void deleteHinhThucThanhToan(Integer id);
    HinhThucThanhToan getHinhThucThanhToanById(Integer id);
    List<HinhThucThanhToan> getAllHinhThucThanhToan();
}
