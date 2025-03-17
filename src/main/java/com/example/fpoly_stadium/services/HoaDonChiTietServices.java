package com.example.fpoly_stadium.services;
import com.example.fpoly_stadium.entity.hoaDon.HoaDon;
import com.example.fpoly_stadium.entity.hoaDon.HoaDonChiTiet;
import com.example.fpoly_stadium.entity.san.SanCa;
import com.example.fpoly_stadium.entity.user.NhanVien;
import com.example.fpoly_stadium.repository.HoaDonChiTietRepository;
import com.example.fpoly_stadium.repository.HoaDonRepository;
import com.example.fpoly_stadium.repository.SanCaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class HoaDonChiTietServices {
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private SanCaRepository sanCaRepository;

    // Tạo mới một hóa đơn chi tiết
    @Transactional
    public HoaDonChiTiet taoHoaDonChiTiet(Integer hoaDonId, Integer sanCaId, Integer ngayDenSan, Integer tienGiamGia, String ghiChu) {
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        SanCa sanCa = sanCaRepository.findById(sanCaId)
                .orElseThrow(() -> new RuntimeException("Sân ca không tồn tại"));

        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
        hoaDonChiTiet.setHoaDon(hoaDon);
        hoaDonChiTiet.setSanCa(sanCa);
        hoaDonChiTiet.setMaHoaDonChiTiet("HDCT" + System.currentTimeMillis());
        hoaDonChiTiet.setNgayDenSan(ngayDenSan);
        hoaDonChiTiet.setTienGiamGia(tienGiamGia != null ? tienGiamGia : 0);
        hoaDonChiTiet.setGhiChu(ghiChu != null ? ghiChu : "Thuê sân ca");

        // Cập nhật tổng tiền của hóa đơn (giả sử không giảm giá)
        hoaDon.setTongTien(hoaDon.getTongTien().add(BigDecimal.valueOf(sanCa.getGia() - (tienGiamGia != null ? tienGiamGia : 0))));
        hoaDonRepository.save(hoaDon);

        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    // Lấy danh sách tất cả hóa đơn chi tiết
    public List<HoaDonChiTiet> layDanhSachHoaDonChiTiet() {
        return hoaDonChiTietRepository.findAll();
    }

    // Lấy hóa đơn chi tiết theo ID
    public HoaDonChiTiet layHoaDonChiTietTheoId(Integer id) {
        return hoaDonChiTietRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn chi tiết không tồn tại với ID: " + id));
    }

    // Cập nhật hóa đơn chi tiết
    @Transactional
    public HoaDonChiTiet capNhatHoaDonChiTiet(Integer id, Integer sanCaId, Integer ngayDenSan, Integer tienGiamGia, String ghiChu) {
        HoaDonChiTiet hoaDonChiTiet = layHoaDonChiTietTheoId(id);
        SanCa sanCa = sanCaRepository.findById(sanCaId)
                .orElseThrow(() -> new RuntimeException("Sân ca không tồn tại"));

        // Cập nhật thông tin
        hoaDonChiTiet.setSanCa(sanCa);
        hoaDonChiTiet.setNgayDenSan(ngayDenSan);
        hoaDonChiTiet.setTienGiamGia(tienGiamGia != null ? tienGiamGia : hoaDonChiTiet.getTienGiamGia());
        hoaDonChiTiet.setGhiChu(ghiChu != null ? ghiChu : hoaDonChiTiet.getGhiChu());

        // Cập nhật lại tổng tiền hóa đơn (giả sử tính toán lại)
        HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
        hoaDon.setTongTien(hoaDon.getTongTien().subtract(BigDecimal.valueOf(sanCa.getGia() - hoaDonChiTiet.getTienGiamGia()))
                .add(BigDecimal.valueOf(sanCa.getGia() - (tienGiamGia != null ? tienGiamGia : 0))));
        hoaDonRepository.save(hoaDon);

        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    // Xóa hóa đơn chi tiết
    @Transactional
    public void xoaHoaDonChiTiet(Integer id) {
        HoaDonChiTiet hoaDonChiTiet = layHoaDonChiTietTheoId(id);
        HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
        hoaDon.setTongTien(hoaDon.getTongTien().subtract(BigDecimal.valueOf(hoaDonChiTiet.getSanCa().getGia() - hoaDonChiTiet.getTienGiamGia())));
        hoaDonRepository.save(hoaDon);
        hoaDonChiTietRepository.deleteById(id);
    }

    // Lấy danh sách hóa đơn chi tiết theo hóa đơn
    public List<HoaDonChiTiet> layDanhSachTheoHoaDon(Integer hoaDonId) {
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        return hoaDonChiTietRepository.findByHoaDon(hoaDon);
    }
}
