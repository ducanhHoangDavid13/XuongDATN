package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.entity.san.NgayTrongTuan;
import com.example.fpoly_stadium.entity.san.SanBong;
import com.example.fpoly_stadium.entity.san.SanCa;
import com.example.fpoly_stadium.repository.SanCaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanCaService {
    @Autowired
    private SanCaRepository sanCaRepository;

    // Lấy tất cả các SanCa
    public List<SanCa> getAllSanCas() {
        return sanCaRepository.findAll();
    }

    // Lấy SanCa theo ID
    public Optional<SanCa> getSanCaById(Integer id) {
        return sanCaRepository.findById(id);
    }

    // Lấy SanCa theo ngày trong tuần
    public List<SanCa> getSanCasByNgayTrongTuan(NgayTrongTuan ngayTrongTuan) {
        return sanCaRepository.findByNgayTrongTuan(ngayTrongTuan);
    }

    // Tạo mới SanCa
    public SanCa createSanCa(SanCa sanCa) {
        return sanCaRepository.save(sanCa);
    }

    // Cập nhật một SanCa
    public SanCa updateSanCa(SanCa sanCa) {
        return sanCaRepository.save(sanCa);
    }

    // Xóa SanCa
    public void deleteSanCa(Integer id) {
        sanCaRepository.deleteById(id);
    }

    // Kiểm tra xem SanCa có trống không
    public boolean checkSanCaAvailability(Long sanBongId, NgayTrongTuan ngayTrongTuan) {
        List<SanCa> sanCas = sanCaRepository.findByNgayTrongTuan(ngayTrongTuan);
        for (SanCa sanCa : sanCas) {
            if (sanCa.getSanBong().getId().equals(sanBongId) && sanCa.getTrangThai()) {
                return true;  // Sân còn trống
            }
        }
        return false;  // Không còn sân trống
    }
}
