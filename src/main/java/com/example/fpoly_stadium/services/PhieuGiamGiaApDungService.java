package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.PhieuGiamGiaApDung;

import java.util.List;
import java.util.Optional;

public interface PhieuGiamGiaApDungService {
    PhieuGiamGiaApDung createPhieuGiamGiaApDung(PhieuGiamGiaApDung phieu);
    PhieuGiamGiaApDung updatePhieuGiamGiaApDung(PhieuGiamGiaApDung phieu);
    void deletePhieuGiamGiaApDung(Integer id);
    Optional<PhieuGiamGiaApDung> getPhieuGiamGiaApDungById(Integer id);
        List<PhieuGiamGiaApDung> getAllPhieuGiamGiaApDung();
}
