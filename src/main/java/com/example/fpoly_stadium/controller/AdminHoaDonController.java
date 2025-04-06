package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.model.ChiTietHoaDon; // Import nếu cần hiển thị chi tiết
import com.example.fpoly_stadium.model.HoaDon;
import com.example.fpoly_stadium.model.PhuPhiHoaDon; // Import nếu cần hiển thị chi tiết
// Import các Service cần thiết
import com.example.fpoly_stadium.services.HoaDonService;
import com.example.fpoly_stadium.services.CTHoaDonService; // Service Chi Tiết Hóa Đơn
import com.example.fpoly_stadium.services.PhuPhiHoaDonService; // Service Phụ Phí
// import com.example.fpoly_stadium.services.PhieuGiamGiaApDungService; // Service Phiếu Áp Dụng
// import com.example.fpoly_stadium.services.AccountService; // Service Tài khoản (Nhân viên)
// import com.example.fpoly_stadium.services.HinhThucThanhToanService; // Service HTTT

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/invoices")
public class AdminHoaDonController {

    private final HoaDonService hoaDonService;
    private final CTHoaDonService ctHoaDonService; // Inject để lấy chi tiết
    private final PhuPhiHoaDonService phuPhiHoaDonService; // Inject để lấy phụ phí
    // Inject các service khác nếu cần

    @Autowired
    public AdminHoaDonController(HoaDonService hoaDonService,
                                 CTHoaDonService ctHoaDonService,
                                 PhuPhiHoaDonService phuPhiHoaDonService
            /*, các service khác */) {
        this.hoaDonService = hoaDonService;
        this.ctHoaDonService = ctHoaDonService;
        this.phuPhiHoaDonService = phuPhiHoaDonService;
    }

    // Hiển thị danh sách Hóa Đơn (có lọc cơ bản)
    @GetMapping
    public String hienThiDanhSachHoaDon(Model model,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay,
                                        @RequestParam(required = false) String trangThaiThanhToan,
                                        // @RequestParam(required = false) Integer nhanVienId,
                                        @ModelAttribute("thongBaoThanhCong") String thongBaoThanhCong,
                                        @ModelAttribute("thongBaoLoi") String thongBaoLoi) {

        // TODO: Implement logic lọc hóa đơn trong Service dựa trên các tham số
        // List<HoaDon> danhSachHoaDon = hoaDonService.findHoaDonFiltered(tuNgay, denNgay, trangThaiThanhToan, nhanVienId);
        List<HoaDon> danhSachHoaDon = hoaDonService.getAllHoaDon(); // Tạm lấy tất cả

        model.addAttribute("danhSachHoaDon", danhSachHoaDon);
        model.addAttribute("tieuDeTrang", "Quản Lý Hóa Đơn");
        // Thêm các giá trị lọc vào model
        model.addAttribute("tuNgayLoc", tuNgay);
        model.addAttribute("denNgayLoc", denNgay);
        model.addAttribute("trangThaiLoc", trangThaiThanhToan);
        // model.addAttribute("nhanVienIdLoc", nhanVienId);
        // Thêm danh sách trạng thái, nhân viên...
        model.addAttribute("dsTrangThaiHD", List.of("ChuaThanhToan", "DaThanhToan", "DaHuy"));
        // model.addAttribute("dsNhanVien", accountService.getAccountsByRole("NhanVien"));

        if (thongBaoThanhCong != null && !thongBaoThanhCong.isEmpty()) model.addAttribute("thongBaoThanhCong", thongBaoThanhCong);
        if (thongBaoLoi != null && !thongBaoLoi.isEmpty()) model.addAttribute("thongBaoLoi", thongBaoLoi);
        return "admin/invoices/list"; // View list.html
    }

    // Xem chi tiết Hóa Đơn
    @GetMapping("/{id}") // Dùng /{id} thay vì /details cho chuẩn RESTful hơn
    public String xemChiTietHoaDon(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            HoaDon hoaDon = hoaDonService.getHoaDonById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Hóa đơn với ID: " + id));

            // Lấy danh sách chi tiết hóa đơn (dịch vụ)
            List<ChiTietHoaDon> chiTietList = ctHoaDonService.findByHoaDonId(id); // *** Cần tạo phương thức này trong Service/Repo ***

            // Lấy danh sách phụ phí
            List<PhuPhiHoaDon> phuPhiList = phuPhiHoaDonService.findByHoaDonId(id); // *** Cần tạo phương thức này trong Service/Repo ***

            // Lấy thông tin phiếu giảm giá đã áp dụng (nếu có)
            // Optional<PhieuGiamGiaApDung> phieuApDungOpt = phieuGiamGiaApDungService.findByHoaDonId(id);

            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("chiTietList", chiTietList);
            model.addAttribute("phuPhiList", phuPhiList);
            // phieuApDungOpt.ifPresent(phieuGiamGiaApDung -> model.addAttribute("phieuApDung", phieuGiamGiaApDung));
            model.addAttribute("tieuDeTrang", "Chi Tiết Hóa Đơn ID: " + id);

            return "admin/invoices/detail"; // View detail.html
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
            return "redirect:/admin/invoices";
        }
    }

    // Admin hủy Hóa Đơn (chỉ khi chưa thanh toán)
    @PostMapping("/{id}/cancel")
    public String adminHuyHoaDon(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            HoaDon hoaDon = hoaDonService.getHoaDonById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Hóa đơn với ID: " + id));

            if("ChuaThanhToan".equals(hoaDon.getTrangThai())) {
                hoaDon.setTrangThai("DaHuy");
                // TODO: Có thể cần cập nhật lại trạng thái LichDat liên quan
                hoaDonService.updateHoaDon(hoaDon);
                redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Đã hủy hóa đơn ID: " + id + " thành công.");
            } else {
                redirectAttributes.addFlashAttribute("thongBaoLoi", "Không thể hủy hóa đơn đã thanh toán hoặc đã hủy.");
            }
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi khi hủy hóa đơn: " + e.getMessage());
        }
        return "redirect:/admin/invoices";
    }

    // Admin có thể cần chức năng đánh dấu hóa đơn là đã thanh toán (nếu cần ghi nhận thanh toán thủ công)
    @PostMapping("/{id}/mark-paid")
    public String danhDauDaThanhToan(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            HoaDon hoaDon = hoaDonService.getHoaDonById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Hóa đơn với ID: " + id));

            if("ChuaThanhToan".equals(hoaDon.getTrangThai())) {
                hoaDon.setTrangThai("DaThanhToan");
                // TODO: Cập nhật hình thức thanh toán nếu cần (vd: Tiền mặt)
                // hoaDon.setHinhThucThanhToan(...);
                // Cập nhật trạng thái LichDat thành 'HoanThanh'
                // LichDat lichDat = hoaDon.getLichDat();
                // lichDat.setTrangThai("HoanThanh");
                // lichDatService.updateBooking(lichDat);
                hoaDonService.updateHoaDon(hoaDon);
                redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Đã đánh dấu hóa đơn ID: " + id + " là Đã Thanh Toán.");
            } else {
                redirectAttributes.addFlashAttribute("thongBaoLoi", "Hóa đơn này không ở trạng thái Chưa Thanh Toán.");
            }
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi khi đánh dấu thanh toán: " + e.getMessage());
        }
        return "redirect:/admin/invoices";
    }

}
