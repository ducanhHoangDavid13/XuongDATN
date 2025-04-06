package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.PhieuGiamGia;
import com.example.fpoly_stadium.repository.PhieuGiamGiaRepository;
import com.example.fpoly_stadium.services.PhieuGiamGiaService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
@Service
public class PhieuGiamGiaServiceImpl implements PhieuGiamGiaService {
    private final PhieuGiamGiaRepository repository;

    @Autowired
    public PhieuGiamGiaServiceImpl(PhieuGiamGiaRepository repository) {
        this.repository = repository;
    }

    @Override
    public PhieuGiamGia createPhieuGiamGia(PhieuGiamGia phieu) {
        return repository.save(phieu);
    }

    @Override
    public PhieuGiamGia updatePhieuGiamGia(PhieuGiamGia phieu) {
        if (!repository.existsById(phieu.getId())) {
            throw new RuntimeException("PhieuGiamGia không tồn tại với id: " + phieu.getId());
        }
        return repository.save(phieu);
    }

    @Override
    public void deletePhieuGiamGia(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("PhieuGiamGia không tồn tại với id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<PhieuGiamGia> getPhieuGiamGiaById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<PhieuGiamGia> getAllPhieuGiamGia() {
        return repository.findAll();
    }

}
