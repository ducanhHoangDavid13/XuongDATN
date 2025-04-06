package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.SanBong;
import com.example.fpoly_stadium.repository.SanBongRepository;
import com.example.fpoly_stadium.services.SanBongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanBongServiceImpl implements SanBongService {
    private final SanBongRepository sanBongRepository;

    @Autowired
    public SanBongServiceImpl(SanBongRepository sanBongRepository) {
        this.sanBongRepository = sanBongRepository;
    }

    @Override
    public SanBong createSanBong(SanBong sanBong) {
        return sanBongRepository.save(sanBong);
    }

    @Override
    public SanBong updateSanBong(SanBong sanBong) {
        if (!sanBongRepository.existsById(sanBong.getId())) {
            throw new RuntimeException("SanBong not found with id: " + sanBong.getId());
        }
        return sanBongRepository.save(sanBong);
    }

    @Override
    public void deleteSanBong(Integer id) {
        if (!sanBongRepository.existsById(id)) {
            throw new RuntimeException("SanBong not found with id: " + id);
        }
        sanBongRepository.deleteById(id);
    }

    @Override
    public SanBong getSanBongById(Integer id) {
        Optional<SanBong> opt = sanBongRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("SanBong not found with id: " + id));
    }

    @Override
    public List<SanBong> getAllSanBong() {
        return sanBongRepository.findAll();
    }
}
