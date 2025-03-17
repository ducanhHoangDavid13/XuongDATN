package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    // Tìm theo mã hóa đơn
    List<HoaDon> findByMaHoaDon(String maHoaDon);

    // Tìm hóa đơn theo trạng thái
    List<HoaDon> findByTrangThai(Integer trangThai);

    // Tìm hóa đơn theo tên khách hàng
    List<HoaDon> findByKhachHang_HoVaTenContainingIgnoreCase(String keyword);

    // Tìm hóa đơn theo tên nhân viên
    List<HoaDon> findByNhanVien_HoVaTenContainingIgnoreCase(String keyword);

    // Tìm hóa đơn theo khoảng ngày tạo
    List<HoaDon> findByNgayTaoBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Tìm hóa đơn theo khoảng tổng tiền
    List<HoaDon> findByTongTienBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Lọc hóa đơn theo ngày, tổng tiền và trạng thái
    @Query("SELECT h FROM HoaDon h WHERE h.maHoaDon LIKE %:maHoaDon% AND h.trangThai = :trangThai")
    List<HoaDon> searchAndFilterHoaDon(
            @Param("maHoaDon") String maHoaDon,
            @Param("tenKhachHang") String tenKhachHang,
            @Param("tenNhanVien") String tenNhanVien,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("trangThai") Integer trangThai);

    List<HoaDon> findByHoaDon(HoaDon hoaDon);
}

