package com.example.fpoly_stadium.services;


import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import com.example.fpoly_stadium.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional
public class VNPayServices {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private SanCaRepository sanCaRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    private static final String VNPAY_TMN_CODE = "ZN3AO1AN"; // Thay bằng vnp_TmnCode
    private static final String VNPAY_HASH_SECRET = "W91OLR0Q8CAXU99Y48XI3H33IYGI78PE"; // Thay bằng vnp_HashSecret
    private static final String VNPAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String VNPAY_RETURN_URL = "https://f25e-2405-4802-1d75-c69d-d812-7180-2680-299d.ngrok-free.app/ban-hang/vnpay-return";

    // Thanh toán hóa đơn qua VNPay
    @Transactional
    public String thanhToanHoaDon(Integer hoaDonId) {
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        if (hoaDon.getTrangThai() == 1) {
            throw new RuntimeException("Hóa đơn đã được thanh toán!");
        }

        // Tạo URL VNPay
        return generateVNPayUrl(hoaDon);
    }

    // Tạo URL thanh toán VNPay
    private String generateVNPayUrl(HoaDon hoaDon) {
        try {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_OrderInfo = "Thanh toan hoa don " + hoaDon.getMaHoaDon();
            String vnp_Amount = String.valueOf(hoaDon.getTongTien().multiply(BigDecimal.valueOf(100)).intValue());
            String vnp_TxnRef = hoaDon.getMaHoaDon();
            String vnp_IpAddr = "127.0.0.1"; // Thay bằng IP thực tế trong production
            String vnp_CreateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            Map<String, String> vnp_Params = new TreeMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", VNPAY_TMN_CODE);
            vnp_Params.put("vnp_Amount", vnp_Amount);
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", VNPAY_RETURN_URL);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            StringBuilder hashData = new StringBuilder();
            for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
                hashData.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)).append("&");
            }
            hashData.setLength(hashData.length() - 1); // Xóa "&" cuối

            String vnp_SecureHash = hmacSHA512(VNPAY_HASH_SECRET, hashData.toString());
            vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

            StringBuilder url = new StringBuilder(VNPAY_URL).append("?");

            for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
                url.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)).append("&");
            }
            url.setLength(url.length() - 1); // Xóa "&" cuối
            return url.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo URL VNPay", e);
        }
    }

    // Xác minh thanh toán
    public boolean verifyPayment(Map<String, String> params) throws Exception {
        String vnp_SecureHash = params.get("vnp_SecureHash");
        if (vnp_SecureHash == null) {
            return false;
        }

        // Xóa SecureHash khỏi params trước khi tạo chữ ký
        Map<String, String> sortedParams = new TreeMap<>(params);
        sortedParams.remove("vnp_SecureHash");

        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            hashData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        hashData.setLength(hashData.length() - 1); // Xóa ký tự `&` cuối cùng

        // So sánh chữ ký
        String expectedHash = hmacSHA512(VNPAY_HASH_SECRET, hashData.toString());
        return expectedHash.equalsIgnoreCase(vnp_SecureHash);
    }

    // HMAC SHA512
    private String hmacSHA512(String key, String data) throws Exception {
        Mac sha512Hmac = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        sha512Hmac.init(keySpec);
        byte[] bytes = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(bytes).toUpperCase();
    }
}
