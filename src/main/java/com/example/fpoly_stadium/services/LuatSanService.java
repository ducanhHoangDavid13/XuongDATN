package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.LuatSan;

import java.util.List;

public interface LuatSanService {
    LuatSan createLuatSan(LuatSan luatSan);
    LuatSan updateLuatSan(LuatSan luatSan);
    void deleteLuatSan(Integer id);
    LuatSan getLuatSanById(Integer id);
    List<LuatSan> getAllLuatSan();
}
