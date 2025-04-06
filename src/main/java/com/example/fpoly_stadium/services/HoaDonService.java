package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.HoaDon;

import java.util.List;
import java.util.Optional;

public interface HoaDonService {
    HoaDon createHoaDon(HoaDon hoaDon);
    HoaDon updateHoaDon(HoaDon hoaDon);
    void deleteHoaDon(Integer id);
    Optional<HoaDon> getHoaDonById(Integer id);
    List<HoaDon> getAllHoaDon();
}
