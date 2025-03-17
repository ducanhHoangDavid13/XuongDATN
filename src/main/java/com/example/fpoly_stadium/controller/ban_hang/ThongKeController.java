package com.example.fpoly_stadium.controller.ban_hang;
import com.example.fpoly_stadium.services.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
@Controller
@RequestMapping("/thong-ke")
public class ThongKeController {
    @Autowired
    private ThongKeService thongKeService;
//
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String thongKePage(Model model,
//                              @RequestParam(name = "ngay", required = false) String ngay,
//                              @RequestParam(name = "thang", required = false) Integer thang,
//                              @RequestParam(name = "nam", required = false) Integer nam) {
//        if (ngay != null) {
//            LocalDate date = LocalDate.parse(ngay);
//            BigDecimal doanhThuNgay = thongKeService.thongKeDoanhThuTheoNgay(date);
//            int soHoaDonNgay = thongKeService.thongKeSoHoaDonTheoNgay(date);
//            model.addAttribute("doanhThuNgay", doanhThuNgay);
//            model.addAttribute("soHoaDonNgay", soHoaDonNgay);
//        }
//        if (thang != null && nam != null) {
//            BigDecimal doanhThuThang = thongKeService.thongKeDoanhThuTheoThang(nam, thang);
//            int soHoaDonThang = thongKeService.thongKeSoHoaDonTheoThang(nam, thang);
//            model.addAttribute("doanhThuThang", doanhThuThang);
//            model.addAttribute("soHoaDonThang", soHoaDonThang);
//        }
//        if (nam != null) {
//            BigDecimal doanhThuNam = thongKeService.thongKeDoanhThuTheoNam(nam);
//            int soHoaDonNam = thongKeService.thongKeSoHoaDonTheoNam(nam);
//            model.addAttribute("doanhThuNam", doanhThuNam);
//            model.addAttribute("soHoaDonNam", soHoaDonNam);
//        }

//        // Thống kê theo phương thức thanh toán
//        model.addAttribute("doanhThuTheoPTTT", thongKeService.thongKeDoanhThuTheoPhuongThucThanhToan());
//
//        // Thống kê sản phẩm bán chạy nhất
//        model.addAttribute("sanPhamBanChay", thongKeService.thongKeSanPhamBanChay());
//
//        // Thống kê tổng số khách hàng
//        model.addAttribute("tongKhachHang", thongKeService.thongKeTongKhachHang());

//        return "ban-hang/thong-ke";

}
