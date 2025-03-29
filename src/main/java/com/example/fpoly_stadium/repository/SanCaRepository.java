package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.san.NgayTrongTuan;
import com.example.fpoly_stadium.entity.san.SanCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanCaRepository extends JpaRepository<SanCa, Integer> {
    // Tìm tất cả các SanCa cho một Ngày trong tuần
    List<SanCa> findByNgayTrongTuan(NgayTrongTuan ngayTrongTuan);

    // Tìm tất cả các SanCa cho một Sân bóng nhất định
    List<SanCa> findBySanBongId(Integer sanBongId);
}
