package com.example.fpoly_stadium.controller.dich_vu;
import com.example.fpoly_stadium.entity.san.DoUong;
import com.example.fpoly_stadium.services.DoUongServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/do-uong")
public class DoUongController {
    @Autowired
    private DoUongServices doUongServices;
    @GetMapping("/")
    public String getAllDoUong(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            Model model) {

        // Gọi duy nhất một phương thức xử lý tìm kiếm & lọc
        List<DoUong> listDoUong = doUongServices.searchAndFilterDoUong(keyword, trangThai);

        // Đưa dữ liệu vào Model
        model.addAttribute("ListDoUong", listDoUong);
        model.addAttribute("keyword", keyword);
        model.addAttribute("trangThai", trangThai);

        return "dich-vu/san-pham";
    }



    // ➕ Thêm Đồ Uống (GET hiển thị form)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("doUong", new DoUong());
        return "dich-vu/add-do-uong";
    }
    @GetMapping("/detail/{id}")
    public String getDetailDoUong(@PathVariable Integer id, Model model) {
        Optional<DoUong> doUong = doUongServices.getDoUongById(id);
        if (doUong.isPresent()) {
            model.addAttribute("item", doUong.get());
            return "dich-vu/detail-do-uong"; // Điều hướng đến trang riêng cho Đồ Uống
        }
        return "redirect:/dich-vu/san-pham";
    }


    // ➕ Thêm Đồ Uống (POST xử lý thêm)
    @PostMapping("/add")
    public String addDoUong(@ModelAttribute DoUong doUong) {
        doUongServices.saveDoUong(doUong);
        return "redirect:/dich-vu/san-pham";

    }

    // ✏️ Cập nhật Đồ Uống (GET hiển thị form)
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        DoUong doUong = doUongServices.getDoUongById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đồ thuê với ID: " + id));
        model.addAttribute("doUong", doUong);
        return "dich-vu/edit-do-uong";
    }

    // ✏️ Cập nhật Đồ Uống (POST xử lý cập nhật)
    @PostMapping("/update/{id}")
    public String updateDoUong(@PathVariable Integer id, @ModelAttribute DoUong doUong) {
        doUongServices.updateDoUong(id, doUong);
        return "redirect:/dich-vu/san-pham";
    }

    // 🗑 Xóa Đồ Uống
    @GetMapping("/delete/{id}")
    public String deleteDoUong(@PathVariable Integer id) {
        doUongServices.deleteDoUong(id);
        return "redirect:/dich-vu/san-pham";
    }
}
