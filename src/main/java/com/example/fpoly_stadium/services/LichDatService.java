package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.LichDat;

import java.time.LocalDate;
import java.util.List;

public interface LichDatService {
    LichDat createLichDat(LichDat lichDat);
    LichDat updateLichDat(LichDat lichDat);
    void deleteLichDat(Integer id);
    LichDat getLichDatById(Integer id);
    List<LichDat> getAllLichDat();
    List<LichDat> getLichDatByDate(LocalDate date);
    List<LichDat> getLichDatByStatus(String status);
}
