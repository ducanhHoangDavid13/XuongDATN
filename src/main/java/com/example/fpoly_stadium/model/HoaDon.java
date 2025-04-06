package com.example.fpoly_stadium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Lịch đặt: mỗi hóa đơn chỉ có 1 lịch đặt và mỗi lịch đặt chỉ có 1 hóa đơn (unique)
    @NotNull(message = "Lịch đặt không được để trống")
    @OneToOne
    @JoinColumn(name = "lich_dat_id", nullable = false, unique = true)
    private LichDat lichDat;

    // Nhân viên lập hóa đơn: có thể null, nếu hệ thống tự tạo hoặc nhân viên bị xóa
    @ManyToOne
    @JoinColumn(name = "nhan_vien_lap_id", nullable = true)
    private Account nhanVienLap;

    // Ngày lập: mặc định là thời điểm hiện tại
    @Column(name = "ngay_lap", nullable = false)
    private LocalDateTime ngayLap;

    // Tổng tiền sân, dịch vụ, phụ phí, giảm giá, thanh toán: giá trị >= 0 với precision 12, scale 2
    @NotNull(message = "Tổng tiền sân không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tổng tiền sân phải >= 0")
    @Column(name = "tong_tien_san", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongTienSan = BigDecimal.ZERO;

    @NotNull(message = "Tổng tiền dịch vụ không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tổng tiền dịch vụ phải >= 0")
    @Column(name = "tong_tien_dich_vu", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongTienDichVu = BigDecimal.ZERO;

    @NotNull(message = "Tổng tiền phụ phí không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tổng tiền phụ phí phải >= 0")
    @Column(name = "tong_tien_phu_phi", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongTienPhuPhi = BigDecimal.ZERO;

    @NotNull(message = "Tổng tiền giảm giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tổng tiền giảm giá phải >= 0")
    @Column(name = "tong_tien_giam_gia", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongTienGiamGia = BigDecimal.ZERO;

    @NotNull(message = "Tổng thanh toán không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tổng thanh toán phải >= 0")
    @Column(name = "tong_thanh_toan", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongThanhToan = BigDecimal.ZERO;

    // Hình thức thanh toán: có thể null nếu chưa thanh toán
    @ManyToOne
    @JoinColumn(name = "hinh_thuc_thanh_toan_id", nullable = true)
    private HinhThucThanhToan hinhThucThanhToan;

    @Size(max = 255, message = "Mã giao dịch online không vượt quá 255 ký tự")
    @Column(name = "ma_giao_dich_online")
    private String maGiaoDichOnline;

    // Trạng thái: chỉ chấp nhận các giá trị 'ChuaThanhToan', 'DaThanhToan', 'DaHuy'
    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(ChuaThanhToan|DaThanhToan|DaHuy)$", message = "Trạng thái không hợp lệ")
    @Column(name = "trang_thai", nullable = false)
    private String trangThai = "ChuaThanhToan";

    @Size(max = 500, message = "Ghi chú không vượt quá 500 ký tự")
    @Column(name = "ghi_chu")
    private String ghiChu;

    // Thiết lập giá trị mặc định cho ngayLap nếu chưa được set khi persist
    @PrePersist
    public void prePersist() {
        if (this.ngayLap == null) {
            this.ngayLap = LocalDateTime.now();
        }
    }
}
