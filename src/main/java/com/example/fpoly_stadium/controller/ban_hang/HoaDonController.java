package com.example.fpoly_stadium.controller.ban_hang;

import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import com.example.fpoly_stadium.repository.HoaDonRepository;
import com.example.fpoly_stadium.services.HoaDonServices;
import com.example.fpoly_stadium.services.VNPayServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private VNPayServices vnPayServices;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    // Lấy tất cả hóa đơn với bộ lọc tìm kiếm
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

        // Chuyển đổi ngày tháng từ chuỗi
        LocalDateTime startDateTime = parseDateTime(startDate);
        LocalDateTime endDateTime = parseDateTime(endDate);

        // Tìm kiếm và lọc hóa đơn
        List<HoaDon> listHoaDon = hoaDonServices.searchAndFilterHoaDon(
                keyword, trangThai, startDateTime, endDateTime, minPrice, maxPrice,
                tenKhachHang, tenNhanVien, hinhThucThanhToan
        );

        // Thêm thông tin vào model
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

        return "ban-hang/hoa-don"; // View hiển thị danh sách hóa đơn
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

            if (hoaDon.getKhachHang() == null || hoaDon.getNhanVien() == null || hoaDon.getTongTien() == null) {
                return createErrorResponse("Khách hàng, nhân viên và tổng tiền là bắt buộc.");
            }

            HoaDon savedHoaDon = hoaDonServices.saveHoaDon(hoaDon);
            response.put("success", true);
            response.put("id", savedHoaDon.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Đã xảy ra lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateHoaDon(@PathVariable Integer id, @RequestBody HoaDon updatedHoaDon) {
        try {
            if (updatedHoaDon.getMaHoaDon() == null || updatedHoaDon.getMaHoaDon().isEmpty()) {
                return createErrorResponse("Mã hóa đơn không được để trống.");
            }

            // Kiểm tra và xử lý hình thức thanh toán (nếu cần)
            if (updatedHoaDon.getHinhThucThanhToan() != null && updatedHoaDon.getHinhThucThanhToan().equals("VNPAY")) {
                // Logic xử lý VnPay
                // Thực hiện các thao tác cần thiết với VnPay ở đây
            }

            // Cập nhật hóa đơn
            HoaDon updatedHoaDonResult = hoaDonServices.updateHoaDon(id, updatedHoaDon);

            // Tạo phản hồi thành công
            ResponseEntity<Map<String, Object>> response = createSuccessResponse();
            Map<String, Object> responseBody = response.getBody();  // Get the map from the ResponseEntity

            // Add the ID of the updated HoaDon to the response body
            responseBody.put("id", updatedHoaDonResult.getId());

            return ResponseEntity.ok(responseBody);  // Return the updated response

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
    @PostMapping("/thanh-toan")
    public String thanhToanHoaDon(@RequestParam Integer hoaDonId,
                                  @RequestParam String hinhThucThanhToan,
                                  Model model) {
        try {
            if ("VNPay".equalsIgnoreCase(hinhThucThanhToan)) {
                // Tạo URL thanh toán VNPay
                String vnpUrl = vnPayServices.thanhToanHoaDon(hoaDonId);
                return "redirect:" + vnpUrl; // Chuyển hướng người dùng đến VNPay
            } else {
                // Thanh toán qua phương thức khác (có thể là trực tiếp hoặc thanh toán offline)
                HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
                hoaDon.setTrangThai(1); // Đánh dấu là đã thanh toán
                hoaDon.setGhiChu("Thanh toán bằng " + hinhThucThanhToan);
                hoaDonRepository.save(hoaDon);
                model.addAttribute("message", "Thanh toán thành công! Mã hóa đơn: " + hoaDon.getMaHoaDon());
                return "ket-qua-thanh-toan";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi thanh toán: " + e.getMessage());
            return "ban_hang/ban-hang";
        }
    }

    // Xử lý kết quả từ VNPay
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
