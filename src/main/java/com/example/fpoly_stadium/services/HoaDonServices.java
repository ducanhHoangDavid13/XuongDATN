package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import com.example.fpoly_stadium.repository.HoaDonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class HoaDonServices {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    // Lấy danh sách tất cả hóa đơn
    public List<HoaDon> getAllHoaDons() {
        return hoaDonRepository.findAll();
    }

    // Tìm hóa đơn theo ID
    public Optional<HoaDon> getHoaDonById(Integer id) {
        return hoaDonRepository.findById(id);
    }

    // Thêm mới hóa đơn
    public HoaDon saveHoaDon(HoaDon hoaDon) {
        if (hoaDon.getNgayTao() == null) {
            hoaDon.setNgayTao(LocalDateTime.now()); // Gán ngày tạo mặc định
        }
        if (hoaDon.getTrangThai() == null) {
            hoaDon.setTrangThai(0); // Mặc định: chưa thanh toán
        }
        return hoaDonRepository.save(hoaDon);
    }

    // Cập nhật hóa đơn
    public HoaDon updateHoaDon(Integer id, HoaDon updatedHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại!"));

        if (updatedHoaDon.getNhanVien() != null) {
            hoaDon.setNhanVien(updatedHoaDon.getNhanVien());
        }
        if (updatedHoaDon.getKhachHang() != null) {
            hoaDon.setKhachHang(updatedHoaDon.getKhachHang());
        }
        if (updatedHoaDon.getHinhThucThanhToan() != null) {
            hoaDon.setHinhThucThanhToan(updatedHoaDon.getHinhThucThanhToan());
        }
        if (updatedHoaDon.getMaHoaDon() != null) {
            hoaDon.setMaHoaDon(updatedHoaDon.getMaHoaDon());
        }
        if (updatedHoaDon.getNgayTao() != null) {
            hoaDon.setNgayTao(updatedHoaDon.getNgayTao());
        }
        if (updatedHoaDon.getTongTien() != null) {
            hoaDon.setTongTien(updatedHoaDon.getTongTien());
        }
        if (updatedHoaDon.getLoaiHoaDon() != null) {
            hoaDon.setLoaiHoaDon(updatedHoaDon.getLoaiHoaDon());
        }
        if (updatedHoaDon.getGhiChu() != null) {
            hoaDon.setGhiChu(updatedHoaDon.getGhiChu());
        }
        if (updatedHoaDon.getTrangThai() != null) {
            hoaDon.setTrangThai(updatedHoaDon.getTrangThai());
        }

        return hoaDonRepository.save(hoaDon);
    }

    // Xóa hóa đơn
    @Transactional
    public void deleteHoaDon(Integer id) {
        if (!hoaDonRepository.existsById(id)) {
            throw new RuntimeException("Hóa đơn không tồn tại!");
        }
        hoaDonRepository.deleteById(id);
    }

    // Tìm hóa đơn theo khách hàng
    public List<HoaDon> findByKhachHang(String tenKhachHang) {
        return hoaDonRepository.findByKhachHang_HoVaTenContainingIgnoreCase(tenKhachHang);
    }

    // Tìm hóa đơn theo nhân viên
    public List<HoaDon> findByNhanVien(String tenNhanVien) {
        return hoaDonRepository.findByNhanVien_TenNhanVienContainingIgnoreCase(tenNhanVien);
    }

    // Lọc hóa đơn theo mã, ngày tạo, tổng tiền, trạng thái
    public List<HoaDon> searchAndFilterHoaDon(
            String maHoaDon, String tenKhachHang, String tenNhanVien,
            LocalDateTime startDate, LocalDateTime endDate,
            BigDecimal minPrice, BigDecimal maxPrice, Integer trangThai) {

        if (maHoaDon != null && !maHoaDon.trim().isEmpty()) {
            return hoaDonRepository.findByMaHoaDon(maHoaDon);
        }
        if (tenKhachHang != null && !tenKhachHang.trim().isEmpty()) {
            return hoaDonRepository.findByKhachHang_HoVaTenContainingIgnoreCase(tenKhachHang);
        }
        if (tenNhanVien != null && !tenNhanVien.trim().isEmpty()) {
            return hoaDonRepository.findByNhanVien_TenNhanVienContainingIgnoreCase(tenNhanVien);
        }
        if (startDate != null && endDate != null) {
            return hoaDonRepository.findByNgayTaoBetween(startDate, endDate);
        }
        if (minPrice != null && maxPrice != null) {
            return hoaDonRepository.findByTongTienBetween(minPrice, maxPrice);
        }
        if (trangThai != null) {
            return hoaDonRepository.findByTrangThai(trangThai);
        }

        return hoaDonRepository.findAll();
    }

    public List<HoaDon> searchAndFilterHoaDon(String keyword, Integer trangThai, LocalDateTime startDateTime, LocalDateTime endDateTime, BigDecimal minPrice, BigDecimal maxPrice, String tenKhachHang, String tenNhanVien, String hinhThucThanhToan) {
        return hoaDonRepository.findAll();
    }
}

