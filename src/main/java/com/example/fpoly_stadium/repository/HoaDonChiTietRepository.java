package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import com.example.fpoly_stadium.entity.hoaDon.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface    HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    List<HoaDonChiTiet> findByHoaDon(HoaDon hoaDon);

    // Lọc theo tên khách hàng
    @Query("SELECT hdc FROM HoaDonChiTiet hdc WHERE hdc.hoaDon.khachHang.hoVaTen LIKE %:tenKhachHang%")
    List<HoaDonChiTiet> findByTenKhachHang(@Param("tenKhachHang") String tenKhachHang);

    // Lọc theo tên nhân viên
    @Query("SELECT hdc FROM HoaDonChiTiet hdc WHERE hdc.hoaDon.nhanVien.tenNhanVien LIKE %:tenNhanVien%")
    List<HoaDonChiTiet> findByTenNhanVien(@Param("tenNhanVien") String tenNhanVien);

//     Lọc theo tên ca sân

}

