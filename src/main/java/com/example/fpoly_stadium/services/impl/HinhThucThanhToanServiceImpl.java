package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.HinhThucThanhToan;
import com.example.fpoly_stadium.repository.HinhThucThanhToanRepository;
import com.example.fpoly_stadium.services.HinhThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HinhThucThanhToanServiceImpl implements HinhThucThanhToanService {
    private final HinhThucThanhToanRepository htttRepository;

    @Autowired
    public HinhThucThanhToanServiceImpl(HinhThucThanhToanRepository htttRepository) {
        this.htttRepository = htttRepository;
    }

    @Override
    public HinhThucThanhToan createHinhThucThanhToan(HinhThucThanhToan httt) {
        return htttRepository.save(httt);
    }

    @Override
    public HinhThucThanhToan updateHinhThucThanhToan(HinhThucThanhToan httt) {
        if (!htttRepository.existsById(httt.getId())) {
            throw new RuntimeException("HinhThucThanhToan not found with id: " + httt.getId());
        }
        return htttRepository.save(httt);
    }

    @Override
    public void deleteHinhThucThanhToan(Integer id) {
        if (!htttRepository.existsById(id)) {
            throw new RuntimeException("HinhThucThanhToan not found with id: " + id);
        }
        htttRepository.deleteById(id);
    }

    @Override
    public HinhThucThanhToan getHinhThucThanhToanById(Integer id) {
        Optional<HinhThucThanhToan> opt = htttRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("HinhThucThanhToan not found with id: " + id));
    }

    @Override
    public List<HinhThucThanhToan> getAllHinhThucThanhToan() {
        return htttRepository.findAll();
    }
}
