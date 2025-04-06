package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.LoaiDichVu;

import java.util.List;

public interface LoaiDichVuService {
    LoaiDichVu createLoaiDichVu(LoaiDichVu loai);
    LoaiDichVu updateLoaiDichVu(LoaiDichVu loai);
    void deleteLoaiDichVu(Integer id);
    LoaiDichVu getLoaiDichVuById(Integer id);
    List<LoaiDichVu> getAllLoaiDichVu();
}
