package com.example.fpoly_stadium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ChiTietHoaDon")
public class ChiTietHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Khóa ngoại đến HoaDon, nếu hóa đơn bị xóa sẽ cascade
    @NotNull(message = "Hóa đơn không được để trống")
    @ManyToOne
    @JoinColumn(name = "hoa_don_id", nullable = false)
    private HoaDon hoaDon;

    // Khóa ngoại đến DichVu
    @NotNull(message = "Dịch vụ không được để trống")
    @ManyToOne
    @JoinColumn(name = "dich_vu_id", nullable = false)
    private DichVu dichVu;

    // Tên dịch vụ tại thời điểm đặt, không được null, tối đa 100 ký tự
    @NotBlank(message = "Tên dịch vụ tại thời điểm không được để trống")
    @Size(max = 100, message = "Tên dịch vụ tại thời điểm không vượt quá 100 ký tự")
    @Column(name = "ten_dich_vu_tai_thoi_diem", nullable = false)
    private String tenDichVuTaiThoiDiem;

    // Số lượng, phải lớn hơn 0, mặc định là 1
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong = 1;

    // Đơn giá tại thời điểm, với precision 12 và scale 2
    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Đơn giá phải lớn hơn 0")
    @Column(name = "don_gia_tai_thoi_diem", nullable = false, precision = 12, scale = 2)
    private BigDecimal donGiaTaiThoiDiem;

    // Thành tiền được tính: (soLuong * donGiaTaiThoiDiem) – cột computed, không insert hay update
    @Column(name = "thanh_tien", insertable = false, updatable = false, precision = 12, scale = 2)
    private BigDecimal thanhTien;
}
