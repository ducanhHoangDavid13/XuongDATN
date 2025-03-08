package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.entity.user.NhanVien;
import com.example.fpoly_stadium.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienService {
    @Autowired
    private NhanVienRepository repository;

    public List<NhanVien> getAllNhanViens() {
        return repository.findAll();
    }

    public Optional<NhanVien> getNhanVienById(Integer id) {
        return repository.findById(id);
    }

    public void saveNhanVien(NhanVien nhanVien) {
        System.out.println("Đang lưu: " + nhanVien);
        repository.save(nhanVien);  // Đảm bảo repository không null
    }


    public void deleteNhanVien(Integer id) {
        repository.deleteById(id);
    }

    public void updateTrangThai(Integer id, Integer trangThai) {
        Optional<NhanVien> nv = repository.findById(id);
        nv.ifPresent(nhanVien -> {
            nhanVien.setTrangThai(trangThai);
            repository.save(nhanVien);
        });
    }
}
