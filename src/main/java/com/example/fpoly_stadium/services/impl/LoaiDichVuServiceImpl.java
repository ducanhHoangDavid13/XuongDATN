package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.LoaiDichVu;
import com.example.fpoly_stadium.repository.LoaiDichVuRepository;
import com.example.fpoly_stadium.services.LoaiDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiDichVuServiceImpl implements LoaiDichVuService {
    private final LoaiDichVuRepository loaiDichVuRepository;

    @Autowired
    public LoaiDichVuServiceImpl(LoaiDichVuRepository loaiDichVuRepository) {
        this.loaiDichVuRepository = loaiDichVuRepository;
    }

    @Override
    public LoaiDichVu createLoaiDichVu(LoaiDichVu loai) {
        return loaiDichVuRepository.save(loai);
    }

    @Override
    public LoaiDichVu updateLoaiDichVu(LoaiDichVu loai) {
        if (!loaiDichVuRepository.existsById(loai.getId())) {
            throw new RuntimeException("LoaiDichVu not found with id: " + loai.getId());
        }
        return loaiDichVuRepository.save(loai);
    }

    @Override
    public void deleteLoaiDichVu(Integer id) {
        if (!loaiDichVuRepository.existsById(id)) {
            throw new RuntimeException("LoaiDichVu not found with id: " + id);
        }
        loaiDichVuRepository.deleteById(id);
    }

    @Override
    public LoaiDichVu getLoaiDichVuById(Integer id) {
        Optional<LoaiDichVu> opt = loaiDichVuRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("LoaiDichVu not found with id: " + id));
    }

    @Override
    public List<LoaiDichVu> getAllLoaiDichVu() {
        return loaiDichVuRepository.findAll();
    }
}
