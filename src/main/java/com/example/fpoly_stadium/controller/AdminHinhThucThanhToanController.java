package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.model.HinhThucThanhToan;
import com.example.fpoly_stadium.services.HinhThucThanhToanService;
// Import service/repo khác nếu cần check ràng buộc (ví dụ: HoaDonService)
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
@RequestMapping("/admin/payment-methods")
public class AdminHinhThucThanhToanController {

    private final HinhThucThanhToanService htttService;
    // private final HoaDonService hoaDonService;

    @Autowired
    public AdminHinhThucThanhToanController(HinhThucThanhToanService htttService /*, HoaDonService hoaDonService*/) {
        this.htttService = htttService;
        // this.hoaDonService = hoaDonService;
    }

    @GetMapping
    public String hienThiDanhSachVaForm(Model model,
                                        @ModelAttribute("htttMoiHoacSua") HinhThucThanhToan htttAttribute,
                                        @ModelAttribute("thongBaoThanhCong") String thongBaoThanhCong,
                                        @ModelAttribute("thongBaoLoi") String thongBaoLoi) {
        List<HinhThucThanhToan> danhSach = htttService.getAllHinhThucThanhToan();
        model.addAttribute("danhSachHTTT", danhSach);

        if (htttAttribute == null || htttAttribute.getId() == null) {
            if (!model.containsAttribute("org.springframework.validation.BindingResult.htttMoiHoacSua")) {
                model.addAttribute("htttMoiHoacSua", new HinhThucThanhToan());
            }
            model.addAttribute("laChinhSua", false);
        } else {
            model.addAttribute("htttMoiHoacSua", htttAttribute); // Giữ từ redirect (edit)
            model.addAttribute("laChinhSua", true);
        }

        model.addAttribute("tieuDeTrang", "Quản Lý Hình Thức Thanh Toán");
        if (thongBaoThanhCong != null && !thongBaoThanhCong.isEmpty()) model.addAttribute("thongBaoThanhCong", thongBaoThanhCong);
        if (thongBaoLoi != null && !thongBaoLoi.isEmpty()) model.addAttribute("thongBaoLoi", thongBaoLoi);
        return "admin/payment-methods/list-form"; // <<<--- View tiếng Anh
    }

    @PostMapping("/save")
    public String luuHTTT(@Valid @ModelAttribute("htttMoiHoacSua") HinhThucThanhToan httt,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        // TODO: Kiểm tra trùng tên Hình thức thanh toán nếu cần
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.htttMoiHoacSua", bindingResult);
            redirectAttributes.addFlashAttribute("htttMoiHoacSua", httt);
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Dữ liệu nhập không hợp lệ!");
            return "redirect:/admin/payment-methods";
        }
        boolean isNew = (httt.getId() == null);
        try {
            if (isNew) {
                htttService.createHinhThucThanhToan(httt);
                redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Thêm Hình Thức Thanh Toán '" + httt.getTenHinhThuc() + "' thành công!");
            } else {
                htttService.updateHinhThucThanhToan(httt);
                redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Cập nhật Hình Thức Thanh Toán '" + httt.getTenHinhThuc() + "' thành công!");
            }
        } catch (Exception e) {
            String actionType = isNew ? "thêm mới" : "cập nhật";
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi khi " + actionType + " Hình Thức Thanh Toán: " + e.getMessage());
            redirectAttributes.addFlashAttribute("htttMoiHoacSua", httt); // Giữ lại dữ liệu khi lỗi
        }
        return "redirect:/admin/payment-methods";
    }

    @GetMapping("/{id}/edit")
    public String hienThiFormSua(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            HinhThucThanhToan httt = htttService.getHinhThucThanhToanById(id); // Sẽ ném lỗi nếu không tìm thấy
            redirectAttributes.addFlashAttribute("htttMoiHoacSua", httt);
        } catch (RuntimeException e) { // Bắt RuntimeException từ service
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
        }
        return "redirect:/admin/payment-methods";
    }

    @PostMapping("/{id}/delete")
    public String xoaHTTT(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            // *** KIỂM TRA RÀNG BUỘC VỚI HoaDonService ***
            // boolean isInUse = hoaDonService.existsByHinhThucThanhToanId(id);
            // if(isInUse) { throw new DataIntegrityViolationException(...); }
            String tenHTTT = htttService.getHinhThucThanhToanById(id).getTenHinhThuc();
            htttService.deleteHinhThucThanhToan(id);
            redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Đã xóa Hình Thức Thanh Toán '" + tenHTTT + "' thành công!");
        } catch (RuntimeException e) { // Bắt lỗi không tìm thấy hoặc không xóa được
            if (e instanceof DataIntegrityViolationException) {
                redirectAttributes.addFlashAttribute("thongBaoLoi", "Không thể xóa Hình Thức Thanh Toán này vì đang được sử dụng trong Hóa Đơn.");
            } else {
                redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi không mong muốn khi xóa: " + e.getMessage());
        }
        return "redirect:/admin/payment-methods";
    }
}
