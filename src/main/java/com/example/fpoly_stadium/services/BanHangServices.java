        package com.example.fpoly_stadium.services;

        import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
        import com.example.fpoly_stadium.entity.hoaDon.HoaDonChiTiet;
        import com.example.fpoly_stadium.entity.hoaDon.HinhThucThanhToan;
        import com.example.fpoly_stadium.entity.san.SanCa;
        import com.example.fpoly_stadium.entity.user.KhachHang;
        import com.example.fpoly_stadium.entity.user.NhanVien;
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
        import java.util.List;
        import java.util.Map;
        import java.util.TreeMap;

        @Service
        public class BanHangServices {

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

            // Cấu hình VNPay Sandbox (thay bằng thông tin thực từ email VNPay sau khi đăng ký)
            private static final String VNPAY_TMN_CODE = "ZN3AO1AN"; // Thay bằng vnp_TmnCode
            private static final String VNPAY_HASH_SECRET = "W91OLR0Q8CAXU99Y48XI3H33IYGI78PE"; // Thay bằng vnp_HashSecret
            private static final String VNPAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
            private static final String VNPAY_RETURN_URL = "https://f25e-2405-4802-1d75-c69d-d812-7180-2680-299d.ngrok-free.app/ban-hang/vnpay-return";

            // Tạo hóa đơn chờ
            @Transactional
            public HoaDon taoHoaDonCho(Integer nhanVienId, Integer khachHangId) {
                NhanVien nhanVien = nhanVienRepository.findById(nhanVienId)
                        .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
                KhachHang khachHang = khachHangRepository.findById(khachHangId)
                        .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

                HoaDon hoaDon = new HoaDon();
                hoaDon.setNhanVien(nhanVien);
                hoaDon.setKhachHang(khachHang);
                hoaDon.setMaHoaDon("HD" + System.currentTimeMillis());
                hoaDon.setNgayTao(LocalDateTime.now());
                hoaDon.setTongTien(BigDecimal.ZERO);
                hoaDon.setLoaiHoaDon(2); // 2: Thuê sân
                hoaDon.setTrangThai(0); // 0: Chưa thanh toán
                hoaDon.setGhiChu("Hóa đơn chờ thanh toán tại quầy");

                return hoaDonRepository.save(hoaDon);
            }

            // Thêm sân ca vào hóa đơn
            @Transactional
            public HoaDonChiTiet themSanCaVaoHoaDon(Integer hoaDonId, Integer sanCaId, LocalDateTime ngayDenSan) {
                HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                        .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
                SanCa sanCa = sanCaRepository.findById(sanCaId)
                        .orElseThrow(() -> new RuntimeException("Sân ca không tồn tại"));

                HoaDonChiTiet chiTiet = new HoaDonChiTiet();
                chiTiet.setHoaDon(hoaDon);
                chiTiet.setSanCa(sanCa);
                chiTiet.setMaHoaDonChiTiet("HDCT" + System.currentTimeMillis());
                chiTiet.setNgayDenSan(ngayDenSan);
                chiTiet.setTienGiamGia(0); // Không áp dụng giảm giá trong ví dụ này
                chiTiet.setGhiChu("Thuê sân ca");

                // Cập nhật tổng tiền
                BigDecimal giaSanCa = BigDecimal.valueOf(sanCa.getGia());
                hoaDon.setTongTien(hoaDon.getTongTien().add(giaSanCa));
                hoaDonRepository.save(hoaDon);

                return hoaDonChiTietRepository.save(chiTiet);
            }

            // Thanh toán hóa đơn
            @Transactional
            public Object thanhToanHoaDon(Integer hoaDonId, String hinhThucThanhToanStr) {
                // 1. Tìm hóa đơn theo ID
                HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                        .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

                // 2. Kiểm tra hóa đơn đã thanh toán chưa
                if (hoaDon.getTrangThai() == 1) {
                    throw new RuntimeException("Hóa đơn đã được thanh toán!");
                }

                // 3. Nếu là thanh toán bằng VNPay
                if ("VNPay".equalsIgnoreCase(hinhThucThanhToanStr)) {
                    return generateVNPayUrl(hoaDon);
                } else {
                    // 4. Tạo đối tượng thanh toán
                    HinhThucThanhToan thanhToan = new HinhThucThanhToan();

                    // 5. Gán hóa đơn trực tiếp (KHÔNG cần danh sách)
        //            thanhToan.setHoaDon(hoaDon);

                    // 6. Gán hình thức thanh toán
                    thanhToan.setHinhThucThanhToan(hinhThucThanhToanStr);

                    // 7. Gán số tiền
                    thanhToan.setSoTien(BigDecimal.valueOf(hoaDon.getTongTien().doubleValue()));

                    // 8. Lưu vào DB
                    hinhThucThanhToanRepository.save(thanhToan);

                    // 9. Cập nhật trạng thái hóa đơn
                    hoaDon.setTrangThai(1);
                    hoaDon.setGhiChu(hoaDon.getGhiChu() + " - Thanh toán bằng " + hinhThucThanhToanStr);
                    return hoaDonRepository.save(hoaDon);
                }
            }


            // Xem chi tiết hóa đơn
            public HoaDon xemHoaDon(Integer id) {
                return hoaDonRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
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

            // HMAC SHA512
            private String hmacSHA512(String key, String data) throws Exception {
                Mac sha512Hmac = Mac.getInstance("HmacSHA512");
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
                sha512Hmac.init(keySpec);
                byte[] bytes = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
                return HexFormat.of().formatHex(bytes).toUpperCase();
            }
        }