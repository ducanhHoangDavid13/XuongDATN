package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.ChiTietHoaDon;

import java.util.List;
import java.util.Optional;

public interface CTHoaDonService {
    ChiTietHoaDon createChiTietHoaDon(ChiTietHoaDon chiTiet);
    ChiTietHoaDon updateChiTietHoaDon(ChiTietHoaDon chiTiet);
    void deleteChiTietHoaDon(Integer id);
    Optional<ChiTietHoaDon> getChiTietHoaDonById(Integer id);
    List<ChiTietHoaDon> getAllChiTietHoaDon();

    List<ChiTietHoaDon> findByHoaDonId(Integer id);
}
