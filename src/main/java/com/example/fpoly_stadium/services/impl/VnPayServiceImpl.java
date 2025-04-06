package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.HoaDon;
import com.example.fpoly_stadium.repository.HoaDonRepository;
import com.example.fpoly_stadium.services.VnPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class VnPayServiceImpl implements VnPayService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    private static final String VNPAY_TMN_CODE = "ZN3AO1AN";
    private static final String VNPAY_HASH_SECRET = "W91OLR0Q8CAXU99Y48XI3H33IYGI78PE";
    private static final String VNPAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String VNPAY_RETURN_URL = "https://your-domain.com/vnpay-return";

    @Override
    public String thanhToanHoaDon(Integer hoaDonId) {
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        if ("DaThanhToan".equals(hoaDon.getTrangThai())) {
            throw new RuntimeException("Hóa đơn đã được thanh toán!");
        }

        return generateVNPayUrl(hoaDon);
    }

    private String generateVNPayUrl(HoaDon hoaDon) {
        try {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_OrderInfo = "Thanh toán hóa đơn cho lịch đặt #" + hoaDon.getLichDat().getId();
            String vnp_Amount = String.valueOf(
                    hoaDon.getTongThanhToan().multiply(BigDecimal.valueOf(100)).intValue());
            String vnp_TxnRef = "HD" + hoaDon.getId();
            String vnp_IpAddr = "127.0.0.1";
            String vnp_CreateDate = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

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
                hashData.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                        .append("&");
            }
            hashData.setLength(hashData.length() - 1);

            String vnp_SecureHash = hmacSHA512(VNPAY_HASH_SECRET, hashData.toString());
            vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

            StringBuilder url = new StringBuilder(VNPAY_URL).append("?");
            for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
                url.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                        .append("&");
            }
            url.setLength(url.length() - 1);
            return url.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo URL VNPay", e);
        }
    }

    private String hmacSHA512(String key, String data) throws Exception {
        Mac sha512Hmac = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        sha512Hmac.init(keySpec);
        byte[] bytes = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(bytes).toUpperCase();
    }
}
