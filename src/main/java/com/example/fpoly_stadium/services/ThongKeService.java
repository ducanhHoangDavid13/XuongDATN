package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import com.example.fpoly_stadium.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThongKeService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    // Thống kê tổng doanh thu theo ngày
    public BigDecimal thongKeDoanhThuTheoNgay(LocalDate ngay) {
        LocalDateTime startOfDay = ngay.atStartOfDay();
        LocalDateTime endOfDay = ngay.atTime(23, 59, 59);
        List<HoaDon> hoaDons = hoaDonRepository.findByNgayTaoBetween(startOfDay, endOfDay);
        return hoaDons.stream().map(HoaDon::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Thống kê tổng doanh thu theo tháng
    public BigDecimal thongKeDoanhThuTheoThang(int nam, int thang) {
        LocalDateTime startOfMonth = LocalDate.of(nam, thang, 1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        List<HoaDon> hoaDons = hoaDonRepository.findByNgayTaoBetween(startOfMonth, endOfMonth);
        return hoaDons.stream().map(HoaDon::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Thống kê tổng doanh thu theo năm
    public BigDecimal thongKeDoanhThuTheoNam(int nam) {
        LocalDateTime startOfYear = LocalDate.of(nam, 1, 1).atStartOfDay();
        LocalDateTime endOfYear = startOfYear.plusYears(1).minusSeconds(1);
        List<HoaDon> hoaDons = hoaDonRepository.findByNgayTaoBetween(startOfYear, endOfYear);
        return hoaDons.stream().map(HoaDon::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
