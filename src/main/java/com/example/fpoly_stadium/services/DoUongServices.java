package com.example.fpoly_stadium.services;
import com.example.fpoly_stadium.entity.san.DoUong;
import com.example.fpoly_stadium.repository.DoUongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DoUongServices {
    @Autowired
    private DoUongRepository doUongRepository;

    public List<DoUong> getAllDoUong() {
        return doUongRepository.findAll();
    }

    public Optional<DoUong> getDoUongById(Integer id) {
        return doUongRepository.findById(id);
    }

    public void saveDoUong(DoUong doUong) {
        doUongRepository.save(doUong);
    }

    public void updateDoUong(Integer id, DoUong updatedDoUong) {
        Optional<DoUong> existingDoUong = doUongRepository.findById(id);
        if (existingDoUong.isPresent()) {
            DoUong doUong = existingDoUong.get();
            doUong.setTenDoUong(updatedDoUong.getTenDoUong());
            doUong.setDonGia(updatedDoUong.getDonGia());
            doUong.setSoLuong(updatedDoUong.getSoLuong());
            doUong.setTrangThai(updatedDoUong.getTrangThai());
            doUongRepository.save(doUong);
        }
    }

    public void deleteDoUong(Integer id) {
        doUongRepository.deleteById(id);
    }
}
