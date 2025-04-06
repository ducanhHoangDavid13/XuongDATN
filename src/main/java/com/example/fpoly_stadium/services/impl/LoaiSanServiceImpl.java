package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.LoaiSan;
import com.example.fpoly_stadium.repository.LoaiSanRepository;
import com.example.fpoly_stadium.services.LoaiSanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiSanServiceImpl implements LoaiSanService {
    private final LoaiSanRepository loaiSanRepository;

    @Autowired
    public LoaiSanServiceImpl(LoaiSanRepository loaiSanRepository) {
        this.loaiSanRepository = loaiSanRepository;
    }

    @Override
    public LoaiSan createLoaiSan(LoaiSan loaiSan) {
        return loaiSanRepository.save(loaiSan);
    }

    @Override
    public LoaiSan updateLoaiSan(LoaiSan loaiSan) {
        if (!loaiSanRepository.existsById(loaiSan.getId())) {
            throw new RuntimeException("LoaiSan not found with id: " + loaiSan.getId());
        }
        return loaiSanRepository.save(loaiSan);
    }

    @Override
    public void deleteLoaiSan(Integer id) {
        if (!loaiSanRepository.existsById(id)) {
            throw new RuntimeException("LoaiSan not found with id: " + id);
        }
        loaiSanRepository.deleteById(id);
    }

    @Override
    public LoaiSan getLoaiSanById(Integer id) {
        Optional<LoaiSan> opt = loaiSanRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("LoaiSan not found with id: " + id));
    }

    @Override
    public List<LoaiSan> getAllLoaiSan() {
        return loaiSanRepository.findAll();
    }
}
