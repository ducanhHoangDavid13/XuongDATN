package com.example.fpoly_stadium.controller.dich_vu;
import com.example.fpoly_stadium.entity.san.DoThue;
import com.example.fpoly_stadium.services.DoThueServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/do-thue")
public class DoThueController{
    @Autowired
    private DoThueServices doThueServices;
    // Lấy danh sách đồ thuê
    @GetMapping("/")
    public String getAllDoThue(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            Model model) {

        // Gọi một phương thức duy nhất để tìm kiếm & lọc
        List<DoThue> listDoThue = doThueServices.searchAndFilterDoThue(keyword, trangThai);

        // Đưa dữ liệu vào Model
        model.addAttribute("ListDoThue", listDoThue);
        model.addAttribute("keyword", keyword);
        model.addAttribute("trangThai", trangThai);

        return "dich-vu/san-pham";
    }


    @GetMapping("/detail/{id}")
    public String getDoThueDetail(@PathVariable Integer id, Model model) {
        Optional<DoThue> doThue = doThueServices.getDoThueById(id);
        if (doThue.isPresent()) {
            model.addAttribute("item", doThue.get());
            return "dich-vu/detail-do-thue"; // Điều hướng đến trang riêng cho Đồ Thuê
        }
        return "redirect:/dich-vu/san-pham";
    }


    // ➕ Thêm Đồ Thuê (GET hiển thị form)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("doThue", new DoThue());
        return "dich-vu/add-do-thue";
    }

    // ➕ Thêm Đồ Thuê (POST xử lý thêm)
    @PostMapping("/add")
    public String addDoThue(@ModelAttribute DoThue doThue) {
        doThueServices.saveDoThue(doThue);
        return "redirect:/dich-vu/san-pham";
    }

    // ✏️ Cập nhật Đồ Thuê (GET hiển thị form)
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        DoThue doThue = doThueServices.getDoThueById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đồ thuê với ID: " + id));
        model.addAttribute("doThue", doThue);
        return "dich-vu/edit-do-thue";
    }

    // ✏️ Cập nhật Đồ Thuê (POST xử lý cập nhật)
    @PostMapping("/update/{id}")
    public String updateDoThue(@PathVariable Integer id, @ModelAttribute DoThue doThue) {
        doThueServices.updateDoThue(id, doThue);
        return "redirect:/dich-vu/san-pham";
    }

    // 🗑 Xóa Đồ Thuê
    @GetMapping("/delete/{id}")
    public String deleteDoThue(@PathVariable Integer id) {
        doThueServices.deleteDoThue(id);
        return "redirect:/dich-vu/san-pham";
    }
}
