package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.entity.san.SanBong;
import com.example.fpoly_stadium.services.SanBongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sanbong")
public class SanBongController {

    @Autowired
    private SanBongService sanBongService;

    @GetMapping
    public String listSanBong(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<SanBong> sanBongs;
        if (keyword == null || keyword.trim().isEmpty()) {
            sanBongs = sanBongService.getAllSanBong();
        } else {
            sanBongs = sanBongService.searchSanBong(keyword);
        }
        model.addAttribute("sanBongs", sanBongs);
        model.addAttribute("keyword", keyword);
        return "sanbong-list"; // Trả về template sanbong-list.html
    }

    // Hiển thị form thêm sân
    @GetMapping("/them")
    public String showAddForm(Model model) {
        model.addAttribute("sanBong", new SanBong());
        return "sanbong-add";
    }

    // Xử lý thêm sân mới
    @PostMapping("/them")
    public String addSanBong(@ModelAttribute SanBong sanBong) {
        sanBongService.saveSanBong(sanBong);
        return "redirect:/sanbong";
    }

    // Xử lý xoá sân
    @GetMapping("/xoa/{id}")
    public String xoaSanBong(@PathVariable Long id) {
        sanBongService.deleteSanBongById(id);
        return "redirect:/sanbong"; // Quay lại danh sách sau khi xóa
    }

    // Hiển thị form sửa sân
    @GetMapping("/sua/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        SanBong sanBong = sanBongService.getSanBongById(id);
        if (sanBong == null) {
            return "redirect:/sanbong";
        }
        model.addAttribute("sanBong", sanBong);
        return "sanbong-edit";
    }

    // Xử lý cập nhật sân
    @PostMapping("/sua/{id}")
    public String updateSanBong(@PathVariable Long id, @ModelAttribute SanBong sanBong) {
        sanBong.setId(id);
        sanBongService.saveSanBong(sanBong);
        return "redirect:/sanbong";
    }

    // Hiển thị chi tiết sân bóng theo ID
    @GetMapping("/{id}")
    public String chiTietSanBong(@PathVariable Long id, Model model) {
        SanBong sanBong = sanBongService.getSanBongById(id);
        if (sanBong == null) {
            return "redirect:/sanbong";
        }
        model.addAttribute("sanBong", sanBong);
        return "sanbong-detail";
    }
}
