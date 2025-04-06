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
@Table(name = "PhieuGiamGiaApDung")
public class PhieuGiamGiaApDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Phiếu giảm giá không được để trống")
    @ManyToOne
    @JoinColumn(name = "phieu_giam_gia_id", nullable = false)
    private PhieuGiamGia phieuGiamGia;

    // Vì mỗi hóa đơn chỉ áp dụng 1 phiếu giảm giá nên sử dụng OneToOne với unique = true
    @NotNull(message = "Hóa đơn không được để trống")
    @OneToOne
    @JoinColumn(name = "hoa_don_id", nullable = false, unique = true)
    private HoaDon hoaDon;

    @Column(name = "ngay_ap_dung", nullable = false)
    private LocalDateTime ngayApDung;

    @NotNull(message = "Số tiền được giảm không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Số tiền được giảm phải >= 0")
    @Column(name = "so_tien_duoc_giam", nullable = false, precision = 12, scale = 2)
    private BigDecimal soTienDuocGiam;

    @PrePersist
    public void prePersist() {
        if (ngayApDung == null) {
            ngayApDung = LocalDateTime.now();
        }
    }
}
