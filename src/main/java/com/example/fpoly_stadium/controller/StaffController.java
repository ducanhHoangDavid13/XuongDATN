package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.entity.user.NhanVien;
import com.example.fpoly_stadium.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService service;

    //  Hiển thị danh sách nhân viên
    @GetMapping
    public String listNhanViens(Model model) {
        model.addAttribute("nhanViens", service.getAllNhanViens());
        model.addAttribute("nhanVien", new NhanVien()); // Để dùng cho form thêm/sửa trong modal
        return "nhan_vien/index"; // Trang quản lý nhân viên
    }

    //  Thêm hoặc sửa nhân viên
    @PostMapping("/save")
    public String saveNhanVien(@ModelAttribute("nhanVien") NhanVien nhanVien, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("nhanViens", service.getAllNhanViens());
            return "nhan_vien/index"; // Trả về trang với lỗi validation
        }
        service.saveNhanVien(nhanVien);
        return "redirect:/staff";
    }


    //  Hiển thị form chỉnh sửa (trả về JSON để đổ vào modal)
    @GetMapping("/edit/{id}")
    @ResponseBody
    public Optional<NhanVien> getNhanVienById(@PathVariable Integer id) {
        return service.getNhanVienById(id);
    }

    //  Xóa nhân viên
    @GetMapping("/delete/{id}")
    public String deleteNhanVien(@PathVariable Integer id) {
        service.deleteNhanVien(id);
        return "redirect:/staff";
    }

    //  Cập nhật trạng thái nhân viên (1: Hoạt động, 0: Không hoạt động)
    @GetMapping("/updateStatus/{id}/{status}")
    public String updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        service.updateTrangThai(id, status);
        return "redirect:/staff";
    }
}
