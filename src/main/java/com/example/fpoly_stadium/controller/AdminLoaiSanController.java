package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.model.LoaiSan;
import com.example.fpoly_stadium.services.LoaiSanService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/pitch-types")
public class AdminLoaiSanController {

    private final LoaiSanService loaiSanService;

    @Autowired
    public AdminLoaiSanController(LoaiSanService loaiSanService) {
        this.loaiSanService = loaiSanService;
    }

    @GetMapping
    public String hienThiDanhSachVaForm(Model model,
                                        @ModelAttribute("loaiSan") LoaiSan loaiSanAttribute,
                                        @ModelAttribute("thongBaoThanhCong") String thongBaoThanhCong,
                                        @ModelAttribute("thongBaoLoi") String thongBaoLoi) {
        List<LoaiSan> danhSach = loaiSanService.getAllLoaiSan();
        model.addAttribute("danhSachLoaiSan", danhSach);
        // Giữ lại đối tượng lỗi từ redirect nếu có, ngược lại tạo mới
        if (!model.containsAttribute("loaiSan")) { // Kiểm tra xem có đối tượng lỗi không
            model.addAttribute("loaiSan", new LoaiSan());
            model.addAttribute("laChinhSua", false);
        } else {
            model.addAttribute("laChinhSua", ((LoaiSan)model.getAttribute("loaiSan")).getId() != null );
        }

        model.addAttribute("tieuDeTrang", "Quản Lý Loại Sân");
        if (thongBaoThanhCong != null && !thongBaoThanhCong.isEmpty()) model.addAttribute("thongBaoThanhCong", thongBaoThanhCong);
        if (thongBaoLoi != null && !thongBaoLoi.isEmpty()) model.addAttribute("thongBaoLoi", thongBaoLoi);
        return "admin/pitch-types/list-form";
    }

    @PostMapping("/save")
    public String luuLoaiSan(@Valid @ModelAttribute("loaiSan") LoaiSan loaiSan,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        // TODO: Kiểm tra trùng tên nếu cần
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loaiSan", bindingResult);
            redirectAttributes.addFlashAttribute("loaiSan", loaiSan);
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Dữ liệu nhập không hợp lệ!");
            return "redirect:/admin/pitch-types";
        }
        boolean isNew = (loaiSan.getId() == null);
        try {
            if (isNew) {
                loaiSanService.createLoaiSan(loaiSan);
                redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Thêm Loại Sân '" + loaiSan.getTenLoaiSan() + "' thành công!");
            } else {
                loaiSanService.updateLoaiSan(loaiSan);
                redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Cập nhật Loại Sân '" + loaiSan.getTenLoaiSan() + "' thành công!");
            }
        } catch (Exception e) {
            String actionType = isNew ? "thêm mới" : "cập nhật";
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi khi " + actionType + " Loại Sân: " + e.getMessage());
            redirectAttributes.addFlashAttribute("loaiSan", loaiSan); // Gửi lại để điền form
        }
        return "redirect:/admin/pitch-types";
    }

    @GetMapping("/{id}/edit")
    public String hienThiFormSua(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            LoaiSan loaiSan = loaiSanService.getLoaiSanById(id);
            redirectAttributes.addFlashAttribute("loaiSan", loaiSan);
        } catch (RuntimeException e) { // Bắt lỗi từ service impl
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
        }
        return "redirect:/admin/pitch-types";
    }

    @PostMapping("/{id}/delete")
    public String xoaLoaiSan(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            String tenLoai = loaiSanService.getLoaiSanById(id).getTenLoaiSan(); // Lấy tên trước khi xóa
            loaiSanService.deleteLoaiSan(id);
            redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Đã xóa Loại Sân '" + tenLoai + "' thành công!");
        } catch (RuntimeException e) { // Bắt lỗi chung từ service impl
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi không mong muốn khi xóa Loại Sân.");
        }
        return "redirect:/admin/pitch-types";
    }
}