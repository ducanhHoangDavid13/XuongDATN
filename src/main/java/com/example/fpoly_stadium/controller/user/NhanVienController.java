package com.example.fpoly_stadium.controller.user;

import com.example.fpoly_stadium.entity.user.NhanVien;
import com.example.fpoly_stadium.services.NhanVienService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/nhan-vien")
@Slf4j
public class NhanVienController {

    private static final String REDIRECT_STAFF = "redirect:/nhan-vien";

    @Autowired
    private NhanVienService service;

    //  Hiển thị danh sách nhân viên
    @GetMapping
    public String listNhanViens(Model model) {
        log.info("Fetching all staff members");
        model.addAttribute("nhanViens", service.getAllNhanViens());
        model.addAttribute("nhanVien", new NhanVien()); // Để dùng cho form thêm/sửa trong modal
        return "tai-khoan/nhan-vien/nhan-vien"; // Trang quản lý nhân viên
    }

    //  Thêm hoặc sửa nhân viên
    @PostMapping("/save")
    public String saveNhanVien(@Valid @ModelAttribute("nhanVien") NhanVien nhanVien, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation errors while saving staff member: {}", result.getAllErrors());
            model.addAttribute("nhanViens", service.getAllNhanViens());
            model.addAttribute("org.springframework.validation.BindingResult.nhanVien", result);
            model.addAttribute("nhanVien", nhanVien);
            return "tai-khoan/nhan_vien/nhan-vien"; // Trả về trang với lỗi validation
        }
        service.saveNhanVien(nhanVien);
        log.info("Saved staff member with ID: {}", nhanVien.getId());
        return REDIRECT_STAFF;
    }

    //  Hiển thị form chỉnh sửa (trả về JSON để đổ vào modal)
    @GetMapping("/edit/{id}")
    @ResponseBody
    public Optional<NhanVien> getNhanVienById(@PathVariable Integer id) {
        log.info("Fetching staff member with ID: {}", id);
        return service.getNhanVienById(id);
    }

    //  Xóa nhân viên
    @GetMapping("/delete/{id}")
    public String deleteNhanVien(@PathVariable Integer id) {
        service.deleteNhanVien(id);
        log.info("Deleted staff member with ID: {}", id);
        return REDIRECT_STAFF;
    }

    //  Cập nhật trạng thái nhân viên (1: Hoạt động, 0: Không hoạt động)
    @GetMapping("/updateStatus/{id}/{status}")
    public String updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        service.updateTrangThai(id, status);
        log.info("Updated status of staff member with ID: {} to {}", id, status);
        return REDIRECT_STAFF;
    }
}