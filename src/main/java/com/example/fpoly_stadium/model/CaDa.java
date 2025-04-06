package com.example.fpoly_stadium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CaDa")
public class CaDa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50, message = "Tên ca không vượt quá 50 ký tự")
    @Column(name = "ten_ca")
    private String tenCa;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @Column(name = "thoi_gian_bat_dau", nullable = false)
    private LocalTime thoiGianBatDau;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    @Column(name = "thoi_gian_ket_thuc", nullable = false)
    private LocalTime thoiGianKetThuc;

    @NotNull(message = "Giá ca không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá ca phải lớn hơn hoặc bằng 0")
    @Column(name = "gia_ca", nullable = false, precision = 12, scale = 2)
    private BigDecimal giaCa;

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(HoatDong|TamDung)$", message = "Trạng thái phải là HoatDong hoặc TamDung")
    @Column(name = "trang_thai", nullable = false)
    private String trangThai;
}
