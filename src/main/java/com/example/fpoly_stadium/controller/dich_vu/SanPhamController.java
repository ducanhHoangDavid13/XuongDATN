package com.example.fpoly_stadium.controller.dich_vu;

import com.example.fpoly_stadium.entity.san.DoThue;
import com.example.fpoly_stadium.entity.san.DoUong;
import com.example.fpoly_stadium.services.DoThueServices;
import com.example.fpoly_stadium.services.DoUongServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dich-vu")
public class SanPhamController  {
    @Autowired
private DoUongServices doUongService;
    @Autowired
    private DoThueServices doThueServices;

    @GetMapping("/san-pham")
    public String getAllSanPham(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false) Integer trangThai,
            Model model) {

        // Gọi phương thức xử lý tìm kiếm & lọc
        List<DoThue> listDoThue = doThueServices.searchAndFilterDoThue(keyword, trangThai);
        List<DoUong> listDoUong = doUongService.searchAndFilterDoUong(keyword, trangThai);

        // Đưa dữ liệu vào Model
        model.addAttribute("listDoThue", listDoThue);
        model.addAttribute("listDoUong", listDoUong);
        model.addAttribute("keyword", keyword);
        model.addAttribute("trangThai", trangThai);

        return "/dich-vu/san-pham";
    }

    @GetMapping("/loai-san-pham")
    public String filterAndSearch(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "trangThai", required = false) Integer trangThai,
            Model model) {

        // Kết hợp tìm kiếm và lọc
        List<DoUong> listDoUong = doUongService.searchAndFilterDoUong(keyword, trangThai);
        List<DoThue> listDoThue = doThueServices.searchAndFilterDoThue(keyword, trangThai);

        // Đưa dữ liệu vào Model
        model.addAttribute("listDoUong", listDoUong);
        model.addAttribute("listDoThue", listDoThue);
        model.addAttribute("keyword", keyword);
        model.addAttribute("trangThai", trangThai);

        return "dich-vu/san-pham";
    }

    // Xử lý nút Reset (chỉ cần redirect về trang ban đầu)
    @GetMapping("/reset")
    public String resetFilter() {
        return "redirect:/dich-vu/san-pham";
    }

}

