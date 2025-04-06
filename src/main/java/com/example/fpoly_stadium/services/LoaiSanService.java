package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.LoaiSan;

import java.util.List;

public interface LoaiSanService {
    LoaiSan createLoaiSan(LoaiSan loaiSan);
    LoaiSan updateLoaiSan(LoaiSan loaiSan);
    void deleteLoaiSan(Integer id);
    LoaiSan getLoaiSanById(Integer id);
    List<LoaiSan> getAllLoaiSan();
}
