package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.entity.san.SanBong;
import com.example.fpoly_stadium.repository.SanBongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanBongService {

    @Autowired
    private SanBongRepository sanBongRepository;

    public List<SanBong> getAllSanBong() {
        return sanBongRepository.findAll();
    }
    public List<SanBong> searchSanBong(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return sanBongRepository.findAll();
        }
        return sanBongRepository.searchByTenSan(keyword);
    }
    public SanBong getSanBongById(Long id) {
        return sanBongRepository.findById(id).orElse(null);
    }
    public void saveSanBong(SanBong sanBong) {
        sanBongRepository.save(sanBong);
    }
    public void deleteSanBongById(Long id) {
        sanBongRepository.deleteById(id);
    }

}
