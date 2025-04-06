package com.example.fpoly_stadium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LichDat")
public class LichDat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Khách hàng đặt sân (khóa ngoại từ TaiKhoan)
    @NotNull(message = "Khách hàng không được để trống")
    @ManyToOne
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private Account khachHang;

    // Sân bóng (khóa ngoại từ SanBong)
    @NotNull(message = "Sân bóng không được để trống")
    @ManyToOne
    @JoinColumn(name = "san_bong_id", nullable = false)
    private SanBong sanBong;

    // Ca đá (khóa ngoại từ CaDa)
    @NotNull(message = "Ca đá không được để trống")
    @ManyToOne
    @JoinColumn(name = "ca_da_id", nullable = false)
    private CaDa caDa;

    // Nhân viên check-in (khóa ngoại từ TaiKhoan, có thể null)
    @ManyToOne
    @JoinColumn(name = "nhan_vien_checkin_id", nullable = true)
    private Account nhanVienCheckin;

    // Ngày đá (DATE)
    @NotNull(message = "Ngày đá không được để trống")
    @Column(name = "ngay_da", nullable = false)
    private LocalDate ngayDa;

    // Thời gian đặt (DATETIME2(3)) – mặc định là GETDATE()
    @Column(name = "thoi_gian_dat", nullable = false)
    private LocalDateTime thoiGianDat;

    // Thời gian check-in (DATETIME2(3)) – có thể null
    @Column(name = "thoi_gian_checkin")
    private LocalDateTime thoiGianCheckin;

    // Ghi chú (NVARCHAR(500))
    @Size(max = 500, message = "Ghi chú không vượt quá 500 ký tự")
    @Column(name = "ghi_chu")
    private String ghiChu;

    // Số lần đổi lịch (TINYINT) – mặc định là 0, giá trị >= 0
    @Min(value = 0, message = "Số lần đổi lịch phải lớn hơn hoặc bằng 0")
    @Column(name = "so_lan_doi_lich", nullable = false)
    private Integer soLanDoiLich = 0;

    // Trạng thái (NVARCHAR(30)) – chỉ chấp nhận các giá trị đã định nghĩa
    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(ChoXacNhan|DaXacNhan|ChoCheckIn|DaCheckIn|DangDa|HoanThanh|DaHuy|KhongDen)$",
            message = "Trạng thái không hợp lệ")
    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    // Sử dụng PrePersist để thiết lập giá trị mặc định cho thoiGianDat nếu chưa được set
    @PrePersist
    public void prePersist() {
        if (this.thoiGianDat == null) {
            this.thoiGianDat = LocalDateTime.now();
        }
    }
}
