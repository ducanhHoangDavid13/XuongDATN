package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.entity.san.DoThue;
import com.example.fpoly_stadium.entity.san.DoUong;
import com.example.fpoly_stadium.repository.DoThueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoThueServices {

    @Autowired
    private DoThueRepository doThueRepository;

    // 📋 Lấy tất cả đồ thuê
    public List<DoThue> getAllDoThue() {
        return doThueRepository.findAll();
    }

    // 🔎 Lấy đồ thuê theo ID
    public Optional<DoThue> getDoThueById(Integer id) {
        return doThueRepository.findById(id);
    }

    public void saveDoThue(DoThue doThue) {
        doThueRepository.save(doThue);
    }

    public void updateDoThue(Integer id, DoThue updatedDoThue) {
        Optional<DoThue> existingDoThue = doThueRepository.findById(id);
        if (existingDoThue.isPresent()) {
            DoThue doThue = existingDoThue.get();
            doThue.setTenDoThue(updatedDoThue.getTenDoThue());
            doThue.setDonGia(updatedDoThue.getDonGia());
            doThue.setSoLuong(updatedDoThue.getSoLuong());
            doThue.setTrangThai(updatedDoThue.getTrangThai());
            doThueRepository.save(doThue);
        }
    }

    public void deleteDoThue(Integer id) {
        doThueRepository.deleteById(id);
    }

    public List<DoThue> searchAndFilterDoThue(String keyword, Integer trangThai) {
        if (!keyword.isEmpty() && trangThai != null) {
            return doThueRepository.findByTenDoThueContainingIgnoreCaseAndTrangThai(keyword, trangThai);
        } else if (!keyword.isEmpty()) {
            return doThueRepository.findByTenDoThueContainingIgnoreCase(keyword);
        } else if (trangThai != null) {
            return doThueRepository.findByTrangThai(trangThai);
        }
        return doThueRepository.findAll();
    }

}

