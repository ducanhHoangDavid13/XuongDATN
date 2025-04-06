package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.PhuPhiHoaDon;

import java.util.List;
import java.util.Optional;

public interface PhuPhiHoaDonService {
    PhuPhiHoaDon createPhuPhiHoaDon(PhuPhiHoaDon phuPhi);
    PhuPhiHoaDon updatePhuPhiHoaDon(PhuPhiHoaDon phuPhi);
    void deletePhuPhiHoaDon(Integer id);
    Optional<PhuPhiHoaDon> getPhuPhiHoaDonById(Integer id);
    List<PhuPhiHoaDon> getAllPhuPhiHoaDon();

    List<PhuPhiHoaDon> findByHoaDonId(Integer id);
}
