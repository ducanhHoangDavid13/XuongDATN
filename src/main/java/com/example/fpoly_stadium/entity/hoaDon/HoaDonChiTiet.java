package com.example.fpoly_stadium.entity.hoaDon;

import com.example.fpoly_stadium.entity.CommonEntity;
import com.example.fpoly_stadium.entity.san.SanCa;
import com.example.fpoly_stadium.entity.user.NhanVien;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet extends CommonEntity {
    @ManyToOne
    @JoinColumn(name = "id_san_ca", nullable = false)
    @NotNull(message = "Sân ca không được để trống")
    private SanCa sanCa;

    @ManyToOne
    @JoinColumn(name = "id_phieu_giam_gia")
    private PhieuGiamGia phieuGiamGia; // Có thể để null nếu không bắt buộc

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien", nullable = false)
    @NotNull(message = "Nhân viên không được để trống")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don", nullable = false)
    @NotNull(message = "Hóa đơn không được để trống")
    private HoaDon hoaDon;

    @Column(name = "ma_hoa_don_chi_tiet")
    @NotNull(message = "Mã hóa đơn chi tiết không được để trống")
    @Size(max = 50, message = "Mã hóa đơn chi tiết không được vượt quá 50 ký tự")
    private String maHoaDonChiTiet;

    @Column(name = "ngay_den_san")
    @NotNull(message = "Ngày đến sân không được để trống")
    @Min(value = 1, message = "Ngày đến sân phải lớn hơn hoặc bằng 1")
    private Integer ngayDenSan;

    @Column(name = "tien_giam_gia")
    @NotNull(message = "Tiền giảm giá không được để trống")
    @Min(value = 0, message = "Tiền giảm giá phải lớn hơn hoặc bằng 0")
    private Integer tienGiamGia;

    @Column(name = "ghi_chu") // Đảm bảo khớp với cột trong cơ sở dữ liệu
    @Size(max = 255, message = "Ghi chú không được vượt quá 255 ký tự")
    private String ghiChu;

    // Thêm trường trang_thai nếu cần (dựa trên bảng)
    @Column(name = "trang_thai")
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean trangThai;

}
