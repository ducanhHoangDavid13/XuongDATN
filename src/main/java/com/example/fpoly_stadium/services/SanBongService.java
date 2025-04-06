package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.SanBong;
import java.util.List;

public interface SanBongService {
    SanBong createSanBong(SanBong sanBong);
    SanBong updateSanBong(SanBong sanBong);
    void deleteSanBong(Integer id);
    SanBong getSanBongById(Integer id);
    List<SanBong> getAllSanBong();
}
