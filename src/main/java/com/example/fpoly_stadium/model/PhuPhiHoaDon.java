package com.example.fpoly_stadium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PhuPhiHoaDon")

public class PhuPhiHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Hóa đơn không được để trống")
    @ManyToOne
    @JoinColumn(name = "hoa_don_id", nullable = false)
    private HoaDon hoaDon;

    @NotBlank(message = "Tên phụ phí không được để trống")
    @Size(max = 100, message = "Tên phụ phí không vượt quá 100 ký tự")
    @Column(name = "ten_phu_phi", nullable = false)
    private String tenPhuPhi;

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Số tiền phải lớn hơn hoặc bằng 0")
    @Column(name = "so_tien", nullable = false, precision = 12, scale = 2)
    private BigDecimal soTien;

    @Size(max = 500, message = "Ghi chú không vượt quá 500 ký tự")
    @Column(name = "ghi_chu")
    private String ghiChu;
}
