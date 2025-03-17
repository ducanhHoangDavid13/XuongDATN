package com.example.fpoly_stadium.controller.ban_hang;
import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import com.example.fpoly_stadium.entity.hoaDon.HoaDonChiTiet;
import com.example.fpoly_stadium.repository.HoaDonChiTietRepository;
import com.example.fpoly_stadium.repository.HoaDonRepository;
import com.example.fpoly_stadium.repository.SanCaRepository;
import com.example.fpoly_stadium.services.BanHangServices;
import com.example.fpoly_stadium.services.HoaDonChiTietServices;
import com.example.fpoly_stadium.services.HoaDonServices;
import com.example.fpoly_stadium.services.VNPayServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ban-hang")
public class PaymentController {
    @Autowired
    private BanHangServices banHangService;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
private VNPayServices vnPayServices;
    @Autowired
private HoaDonChiTietServices hoaDonChiTietService;
    @Autowired
private HoaDonServices hoaDonServices;
    @Autowired
    private SanCaRepository sanCaRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    // Hiển thị trang bán hàng với bộ lọc
    @GetMapping
    public String hienThiBanHang(Model model,
                                 @RequestParam(required = false) String tenKhachHang,
                                 @RequestParam(required = false) String tenNhanVien,
                                 @RequestParam(required = false) String tenCa) {
        List<HoaDonChiTiet> chiTietList;
        if (tenKhachHang != null && !tenKhachHang.isEmpty()) {
            chiTietList = hoaDonChiTietRepository.findByTenKhachHang(tenKhachHang);
        } else if (tenNhanVien != null && !tenNhanVien.isEmpty()) {
            chiTietList = hoaDonChiTietRepository.findByTenNhanVien(tenNhanVien);
        } else if (tenCa != null && !tenCa.isEmpty()) {
            chiTietList = hoaDonChiTietRepository.findByTenCa(tenCa);
        } else {
            chiTietList = hoaDonChiTietRepository.findAll();
        }
        model.addAttribute("sanCaList", sanCaRepository.findAll());
        model.addAttribute("hoaDon", new HoaDon());
        model.addAttribute("chiTietList", chiTietList);
        model.addAttribute("tenKhachHang", tenKhachHang);
        model.addAttribute("tenNhanVien", tenNhanVien);
        model.addAttribute("tenCa", tenCa);
        return "ban_hang/ban-hang";
    }

    // Tạo hóa đơn chờ
    @PostMapping("/tao-hoa-don")
    public String taoHoaDonCho(@RequestParam Integer nhanVienId,
                               @RequestParam Integer khachHangId,
                               Model model) {
        HoaDon hoaDon = banHangService.taoHoaDonCho(nhanVienId, khachHangId);
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDon(hoaDon);
        if (hoaDon == null) {
            throw new RuntimeException("Không thể tạo hóa đơn.");
        }

        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("chiTietList", chiTietList);
        model.addAttribute("sanCaList", sanCaRepository.findAll());
        return "ban_hang/ban-hang";
    }

    // Thêm sân ca
    @PostMapping("/them-san-ca")
    public String themSanCa(@RequestParam Integer hoaDonId,
                            @RequestParam Integer sanCaId,
                            @RequestParam Integer ngayDenSan,
                            Model model) {
        banHangService.themSanCaVaoHoaDon(hoaDonId, sanCaId, ngayDenSan);
        HoaDon hoaDon = banHangService.xemHoaDon(hoaDonId);
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDon(hoaDon);
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("chiTietList", chiTietList);
        model.addAttribute("sanCaList", sanCaRepository.findAll());
        return "ban_hang/ban-hang";
    }

    // Thanh toán
    @PostMapping("/thanh-toan")
    public String thanhToanHoaDon(@RequestParam Integer hoaDonId,
                                  @RequestParam String hinhThucThanhToan,
                                  Model model) {
        Object result = banHangService.thanhToanHoaDon(hoaDonId, hinhThucThanhToan);
        if (result instanceof String) {
            return "redirect:" + result;
        } else {
            HoaDon hoaDon = (HoaDon) result;
            model.addAttribute("message", "Thanh toán thành công! Mã hóa đơn: " + hoaDon.getMaHoaDon());
            return "ket-qua-thanh-toan";
        }
    }
    @GetMapping("/vnpay-return")
    public String vnpayReturn(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) throws Exception {
        boolean success = vnPayServices.verifyPayment(params);

        if (success) {
            String txnRef = params.get("vnp_TxnRef");
            if (txnRef == null || !txnRef.matches("\\d+")) {
                redirectAttributes.addFlashAttribute("error", "Mã giao dịch không hợp lệ!");
                return "redirect:/ban-hang";
            }

            Integer hoaDonId = Integer.valueOf(txnRef);
            HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                    .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

            hoaDon.setTrangThai(1); // Đánh dấu là đã thanh toán
            hoaDonRepository.save(hoaDon);

            redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Thanh toán thất bại!");
        }

        return "redirect:/ban-hang";
    }


}
