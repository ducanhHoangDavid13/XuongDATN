package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.HoaDon;
import com.example.fpoly_stadium.repository.HoaDonRepository;
import com.example.fpoly_stadium.services.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    private final HoaDonRepository repository;

    @Autowired
    public HoaDonServiceImpl(HoaDonRepository repository) {
        this.repository = repository;
    }

    @Override
    public HoaDon createHoaDon(HoaDon hoaDon) {
        return repository.save(hoaDon);
    }

    @Override
    public HoaDon updateHoaDon(HoaDon hoaDon) {
        if (!repository.existsById(hoaDon.getId())) {
            throw new RuntimeException("HoaDon không tồn tại với id: " + hoaDon.getId());
        }
        return repository.save(hoaDon);
    }

    @Override
    public void deleteHoaDon(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("HoaDon không tồn tại với id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<HoaDon> getHoaDonById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<HoaDon> getAllHoaDon() {
        return repository.findAll();
    }
}
