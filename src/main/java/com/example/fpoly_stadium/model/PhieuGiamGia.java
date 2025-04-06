package com.example.fpoly_stadium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PhieuGiamGia", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ma_giam_gia")
})
public class PhieuGiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Mã giảm giá không được để trống")
    @Size(max = 50, message = "Mã giảm giá không vượt quá 50 ký tự")
    @Column(name = "ma_giam_gia", nullable = false, unique = true)
    private String maGiamGia;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 500, message = "Mô tả không vượt quá 500 ký tự")
    @Column(name = "mo_ta", nullable = false)
    private String moTa;

    @NotBlank(message = "Loại giảm giá không được để trống")
    @Pattern(regexp = "^(PhanTram|SoTien)$", message = "Loại giảm giá phải là PhanTram hoặc SoTien")
    @Size(max = 20)
    @Column(name = "loai_giam_gia", nullable = false)
    private String loaiGiamGia;

    @NotNull(message = "Giá trị phải được cung cấp và > 0")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị phải lớn hơn 0")
    @Column(name = "gia_tri", nullable = false)
    private Double giaTri;

    @DecimalMin(value = "0.0", inclusive = true, message = "Giá trị giảm tối đa phải >= 0")
    @Column(name = "gia_tri_giam_toi_da", precision = 12, scale = 2)
    private BigDecimal giaTriGiamToiDa;

    @DecimalMin(value = "0.0", inclusive = true, message = "Điều kiện hóa đơn tối thiểu phải >= 0")
    @Column(name = "dieu_kien_hoa_don_toi_thieu", precision = 12, scale = 2)
    private BigDecimal dieuKienHoaDonToiThieu;

    @Column(name = "so_luong_phat_hanh")
    private Integer soLuongPhatHanh;

    @Column(name = "so_luong_da_su_dung", nullable = false)
    private Integer soLuongDaSuDung = 0;

    @Column(name = "ngay_bat_dau")
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDate ngayKetThuc;

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(HoatDong|HetHan|TamDung|HetLuot)$",
            message = "Trạng thái phải là HoatDong, HetHan, TamDung hoặc HetLuot")
    @Size(max = 20)
    @Column(name = "trang_thai", nullable = false)
    private String trangThai = "HoatDong";

    // FK đến bảng Hạng Khách Hàng (nếu có)
    @Column(name = "ap_dung_cho_loai_khach_id")
    private Integer apDungChoLoaiKhachId;

    // Constraint: nếu cả ngày bắt đầu và kết thúc đều có giá trị thì ngày kết thúc không được trước ngày bắt đầu
    @AssertTrue(message = "Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu")
    public boolean isNgayKetThucValid() {
        if (ngayKetThuc == null || ngayBatDau == null) return true;
        return !ngayKetThuc.isBefore(ngayBatDau);
    }

    // Constraint: nếu số lượng phát hành có giá trị, số lượng đã sử dụng không vượt quá số lượng phát hành
    @AssertTrue(message = "Số lượng đã sử dụng phải không vượt quá số lượng phát hành")
    public boolean isSoLuongValid() {
        if (soLuongPhatHanh == null) return true;
        return soLuongDaSuDung <= soLuongPhatHanh;
    }
}
