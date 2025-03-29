package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.entity.san.SanCa;
import com.example.fpoly_stadium.services.SanCaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/san-ca")
public class SanCaController {

    @Autowired
    private SanCaService sanCaService;

    // Hiển thị danh sách các SanCa
    @GetMapping("/")
    public String viewAllSanCas(Model model) {
        List<SanCa> sanCas = sanCaService.getAllSanCas();
        model.addAttribute("sanCas", sanCas);
        return "san-ca-list";  // View sẽ hiển thị tất cả SanCa
    }

    // Hiển thị chi tiết một SanCa
    @GetMapping("/{id}")
    public String viewSanCaDetails(@PathVariable Integer id, Model model) {
        Optional<SanCa> sanCa = sanCaService.getSanCaById(id);
        if (sanCa.isPresent()) {
            model.addAttribute("sanCa", sanCa.get());
            return "san-ca-details";  // View chi tiết về SanCa
        } else {
            model.addAttribute("error", "Không tìm thấy SanCa với ID " + id);
            return "error";
        }
    }

    // Thêm mới SanCa (Modal form)
    @PostMapping("/add")
    public String addSanCa(@ModelAttribute SanCa sanCa) {
        sanCaService.createSanCa(sanCa);
        return "redirect:/san-ca/";  // Sau khi thêm mới, redirect về danh sách
    }

    // Cập nhật SanCa
    @PostMapping("/edit")
    public String updateSanCa(@ModelAttribute SanCa sanCa) {
        sanCaService.updateSanCa(sanCa);
        return "redirect:/san-ca/";  // Sau khi chỉnh sửa, redirect về danh sách
    }

    // Xóa SanCa
    @GetMapping("/delete/{id}")
    public String deleteSanCa(@PathVariable Integer id) {
        sanCaService.deleteSanCa(id);
        return "redirect:/san-ca/";  // Sau khi xóa, redirect về danh sách
    }
}
