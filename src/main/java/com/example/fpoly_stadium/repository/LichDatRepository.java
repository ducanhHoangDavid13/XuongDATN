package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.model.LichDat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LichDatRepository extends JpaRepository<LichDat, Integer> {
    boolean existsBySanBongIdAndCaDaIdAndNgayDaAndTrangThaiNot(Integer sanBongId, Integer caDaId, LocalDate ngayDa, String excludeTrangThai);

    List<LichDat> findByNgayDa(LocalDate ngayDa);
    List<LichDat> findByTrangThai(String trangThai);
    List<LichDat> findByKhachHangId(Integer khachHangId);

    // Tìm kiếm nâng cao — có thể dùng @Query nếu cần
    @Query("SELECT l FROM LichDat l WHERE " +
            "(:ngayDa IS NULL OR l.ngayDa = :ngayDa) AND " +
            "(:trangThai IS NULL OR l.trangThai = :trangThai) AND " +
            "(:sanBongId IS NULL OR l.sanBong.id = :sanBongId) AND " +
            "(:khachHangId IS NULL OR l.khachHang.id = :khachHangId)")
    List<LichDat> findBookingsFiltered(
            @Param("ngayDa") LocalDate ngayDa,
            @Param("trangThai") String trangThai,
            @Param("sanBongId") Integer sanBongId,
            @Param("khachHangId") Integer khachHangId
    );

    Optional<Object> findBySanBongIdAndCaDaIdAndNgayDa(Integer sanBongId, Integer caDaId, LocalDate ngayDa);
}
