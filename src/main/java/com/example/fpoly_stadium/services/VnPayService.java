package com.example.fpoly_stadium.services;

public interface VnPayService {
    /**
     * Tạo URL thanh toán VNPay dựa trên thông tin hóa đơn.
     *
     * @param hoaDonId ID của hóa đơn
     * @return URL thanh toán VNPay
     */
    String thanhToanHoaDon(Integer hoaDonId);
}
