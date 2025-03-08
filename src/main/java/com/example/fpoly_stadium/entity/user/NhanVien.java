package com.example.fpoly_stadium.entity.user;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "nhan_vien")
public class NhanVien extends CommonEntity {

    @Column(name = "ma_nhan_vien", unique = true, nullable = false)
    @NotBlank(message = "Mã nhân viên không được để trống")
    private String maNhanVien;

    @Column(name = "ten_nhan_vien", nullable = false)
    @NotBlank(message = "Tên nhân viên không được để trống")
    private String tenNhanVien;

    @Column(name = "mat_khau", nullable = false)
    @NotBlank(message = "Mật khẩu không được để trống")
    private String matKhau;

    @Column(name = "email", unique = true)
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "sdt")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String sdt;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "trang_thai")
    private Integer trangThai;

    public static final class TrangThai {
        public static final Integer HOAT_DONG = 1;
        public static final Integer KHONG_HOAT_DONG = 0;
    }
}