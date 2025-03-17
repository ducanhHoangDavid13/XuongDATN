package com.example.fpoly_stadium.controller.ban_hang;

import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import com.example.fpoly_stadium.services.HoaDonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {
    @Autowired
    private HoaDonServices hoaDonServices;

    @GetMapping("")
    public String getAllHoaDon(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "khachHang", required = false, defaultValue = "") String tenKhachHang,
            @RequestParam(name = "nhanVien", required = false, defaultValue = "") String tenNhanVien,
            @RequestParam(name = "hinhThucThanhToan", required = false, defaultValue = "") String hinhThucThanhToan,
            Model model) {

        LocalDateTime startDateTime = parseDateTime(startDate);
        LocalDateTime endDateTime = parseDateTime(endDate);

        List<HoaDon> listHoaDon = hoaDonServices.searchAndFilterHoaDon(
                keyword, trangThai, startDateTime, endDateTime, minPrice, maxPrice,
                tenKhachHang, tenNhanVien, hinhThucThanhToan
        );

        model.addAttribute("listHoaDon", listHoaDon);
        model.addAttribute("keyword", keyword);
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("khachHang", tenKhachHang);
        model.addAttribute("nhanVien", tenNhanVien);
        model.addAttribute("hinhThucThanhToan", hinhThucThanhToan);

        return "ban-hang/hoa-don";
    }


    // ✅ Thêm hóa đơn
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addHoaDon(@RequestBody HoaDon hoaDon) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (hoaDon.getMaHoaDon() == null || hoaDon.getMaHoaDon().isEmpty()) {
                return createErrorResponse("Mã hóa đơn không được để trống.");
            }

            HoaDon savedHoaDon = hoaDonServices.saveHoaDon(hoaDon);
            response.put("success", true);
            response.put("id", savedHoaDon.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Đã xảy ra lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    // ✅ Cập nhật hóa đơn
    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateHoaDon(@PathVariable Integer id, @RequestBody HoaDon updatedHoaDon) {
        try {
            hoaDonServices.updateHoaDon(id, updatedHoaDon);
            return createSuccessResponse();
        } catch (RuntimeException e) {
            return createErrorResponse("Lỗi khi cập nhật hóa đơn: " + e.getMessage());
        }
    }

    // ✅ Xóa hóa đơn
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteHoaDon(@PathVariable Integer id) {
        try {
            hoaDonServices.deleteHoaDon(id);
            return createSuccessResponse();
        } catch (RuntimeException e) {
            return createErrorResponse("Lỗi khi xóa hóa đơn: " + e.getMessage());
        }
    }

    // ✅ Lấy chi tiết hóa đơn
    @GetMapping("/detail/{id}")
    @ResponseBody
    public ResponseEntity<HoaDon> getHoaDonDetail(@PathVariable Integer id) {
        Optional<HoaDon> hoaDon = hoaDonServices.getHoaDonById(id);
        return hoaDon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Reset bộ lọc tìm kiếm
    @GetMapping("/reset")
    public String resetSearch(Model model) {
        model.addAttribute("listHoaDon", hoaDonServices.getAllHoaDons());
        return "ban-hang/hoa-don";
    }

    // 🔹 Chuyển đổi chuỗi ngày thành LocalDateTime
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    // 🔹 Tạo phản hồi lỗi
    private ResponseEntity<Map<String, Object>> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
    }

    // 🔹 Tạo phản hồi thành công
    private ResponseEntity<Map<String, Object>> createSuccessResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}
