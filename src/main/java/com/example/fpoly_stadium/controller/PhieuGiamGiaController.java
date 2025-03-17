package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.entity.hoaDon.PhieuGiamGia;
import com.example.fpoly_stadium.repository.PhieuGiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/phieu-giam-gia")
public class PhieuGiamGiaController {

    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;

    @GetMapping()
    public String hienThi(Model model) {
        List<PhieuGiamGia> list = phieuGiamGiaRepository.findAll();
        System.out.println(list);
        model.addAttribute("listpg", list);
        return "khuyenMai";
    }

    @PostMapping("/add")
    public ResponseEntity<PhieuGiamGia> add(@RequestBody PhieuGiamGia pg) {
        System.out.println(pg.getNgayKetThuc());
        phieuGiamGiaRepository.save(pg);
        return ResponseEntity.ok(pg);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PhieuGiamGia> detail(@PathVariable Integer id) {
        if (!phieuGiamGiaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(phieuGiamGiaRepository.findById(id).get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PhieuGiamGia> update(@PathVariable Integer id, @RequestBody PhieuGiamGia pg) {
        if (!phieuGiamGiaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pg.setId(id);
        phieuGiamGiaRepository.save(pg);
        return ResponseEntity.ok(pg);
    }
}