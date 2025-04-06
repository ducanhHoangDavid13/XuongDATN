package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.DichVu;

import java.util.List;

public interface DichVuService {
    DichVu createDichVu(DichVu dichVu);
    DichVu updateDichVu(DichVu dichVu);
    void deleteDichVu(Integer id);
    DichVu getDichVuById(Integer id);
    List<DichVu> getAllDichVu();
}
