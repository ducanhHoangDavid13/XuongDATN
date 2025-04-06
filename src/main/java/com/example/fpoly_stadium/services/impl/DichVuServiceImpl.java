package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.DichVu;
import com.example.fpoly_stadium.repository.DichVuRepository;
import com.example.fpoly_stadium.services.DichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DichVuServiceImpl implements DichVuService {
    private final DichVuRepository dichVuRepository;

    @Autowired
    public DichVuServiceImpl(DichVuRepository dichVuRepository) {
        this.dichVuRepository = dichVuRepository;
    }

    @Override
    public DichVu createDichVu(DichVu dichVu) {
        return dichVuRepository.save(dichVu);
    }

    @Override
    public DichVu updateDichVu(DichVu dichVu) {
        if (!dichVuRepository.existsById(dichVu.getId())) {
            throw new RuntimeException("DichVu not found with id: " + dichVu.getId());
        }
        return dichVuRepository.save(dichVu);
    }

    @Override
    public void deleteDichVu(Integer id) {
        if (!dichVuRepository.existsById(id)) {
            throw new RuntimeException("DichVu not found with id: " + id);
        }
        dichVuRepository.deleteById(id);
    }

    @Override
    public DichVu getDichVuById(Integer id) {
        Optional<DichVu> opt = dichVuRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("DichVu not found with id: " + id));
    }

    @Override
    public List<DichVu> getAllDichVu() {
        return dichVuRepository.findAll();
    }
}
