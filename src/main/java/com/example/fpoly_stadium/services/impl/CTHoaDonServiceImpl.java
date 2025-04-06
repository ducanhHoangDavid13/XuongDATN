package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.ChiTietHoaDon;
import com.example.fpoly_stadium.repository.CTHoaDonRepository;
import com.example.fpoly_stadium.services.CTHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CTHoaDonServiceImpl implements CTHoaDonService {
    private final CTHoaDonRepository CTHDrepository;

    @Autowired
    public CTHoaDonServiceImpl(CTHoaDonRepository CTHDrepository) {
        this.CTHDrepository = CTHDrepository;
    }

    @Override
    public ChiTietHoaDon createChiTietHoaDon(ChiTietHoaDon chiTiet) {
        return CTHDrepository.save(chiTiet);
    }

    @Override
    public ChiTietHoaDon updateChiTietHoaDon(ChiTietHoaDon chiTiet) {
        if (!CTHDrepository.existsById(chiTiet.getId())) {
            throw new RuntimeException("ChiTietHoaDon không tồn tại với id: " + chiTiet.getId());
        }
        return CTHDrepository.save(chiTiet);
    }

    @Override
    public void deleteChiTietHoaDon(Integer id) {
        if (!CTHDrepository.existsById(id)) {
            throw new RuntimeException("ChiTietHoaDon không tồn tại với id: " + id);
        }
        CTHDrepository.deleteById(id);
    }

    @Override
    public Optional<ChiTietHoaDon> getChiTietHoaDonById(Integer id) {
        return CTHDrepository.findById(id);
    }
    @Override
    public List<ChiTietHoaDon> findByHoaDonId(Integer hoaDonId) {
        return CTHDrepository.findByHoaDonId(hoaDonId);
    }
    @Override
    public List<ChiTietHoaDon> getAllChiTietHoaDon() {
        return CTHDrepository.findAll();
    }
}
