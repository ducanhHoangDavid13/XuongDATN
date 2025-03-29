package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.entity.san.NgayTrongTuan;
import com.example.fpoly_stadium.repository.NgayTrongTuanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NgayTrongTuanService {
    @Autowired
    private NgayTrongTuanRepository ngayTrongTuanRepository;

    public List<NgayTrongTuan> getAllNgayTrongTuan() {
        return ngayTrongTuanRepository.findAll();
    }

    public NgayTrongTuan getNgayTrongTuanById(Integer id) {
        return ngayTrongTuanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NgayTrongTuan not found"));
    }

    public NgayTrongTuan createNgayTrongTuan(NgayTrongTuan ngayTrongTuan) {
        return ngayTrongTuanRepository.save(ngayTrongTuan);
    }

    public NgayTrongTuan updateNgayTrongTuan(Integer id, NgayTrongTuan ngayTrongTuanDetails) {
        NgayTrongTuan ngayTrongTuan = ngayTrongTuanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NgayTrongTuan not found"));
        ngayTrongTuan.setThuTrongTuan(ngayTrongTuanDetails.getThuTrongTuan());
        return ngayTrongTuanRepository.save(ngayTrongTuan);
    }

    public void deleteNgayTrongTuan(Integer id) {
        NgayTrongTuan ngayTrongTuan = ngayTrongTuanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NgayTrongTuan not found"));
        ngayTrongTuanRepository.delete(ngayTrongTuan);
    }
}
