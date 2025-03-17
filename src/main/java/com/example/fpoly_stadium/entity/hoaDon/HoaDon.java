package com.example.fpoly_stadium.entity.hoaDon;

import com.example.fpoly_stadium.entity.CommonEntity;
import com.example.fpoly_stadium.entity.user.KhachHang;
import com.example.fpoly_stadium.entity.user.NhanVien;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Hoa_Don")
public class HoaDon extends CommonEntity {
    //Khoá
    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    @NotNull(message = "Nhân viên không được trống")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    @NotNull(message = "Khách hàng không được trống")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "id_phuong_thuc_thanh_toan")
    private HinhThucThanhToan hinhThucThanhToan;

    @Column(name = "ma_hoa_don", unique = true, nullable = false)
    @NotEmpty(message = "Mã hóa đơn không được trống")
    private String maHoaDon;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "tong_tien")
    @NotNull(message = "Tổng tiền không được trống")
    @Positive(message = "Tổng tiền phải lớn hơn 0")
    private BigDecimal tongTien;

    @Column(name = "loai_hoa_don")
    private Integer loaiHoaDon; // 1: Bán hàng, 2: Thuê sân, 3: Dịch vụ khác

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai; // 1: Đã thanh toán, 0: Chưa thanh toán


}
