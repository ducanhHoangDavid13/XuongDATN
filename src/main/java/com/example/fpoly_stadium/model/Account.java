package com.example.fpoly_stadium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TaiKhoan", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "so_dien_thoai")
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(max = 50, message = "Tên đăng nhập không được vượt quá 50 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max = 255, message = "Mật khẩu không được vượt quá 255 ký tự")
    @Column(name = "password_hash")
    private String passwordHash;

    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;

    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    @Column(name = "ho_ten")
    private String hoTen;

    @Size(max = 20, message = "Số điện thoại không được vượt quá 20 ký tự")
    @Pattern(regexp = "^[0-9+\\-]*$", message = "Số điện thoại chỉ chứa số và ký tự cơ bản")
    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    @Column(name = "dia_chi")
    private String diaChi;

    @Size(max = 255, message = "Ảnh đại diện không được vượt quá 255 ký tự")
    @Column(name = "anh_dai_dien")
    private String anhDaiDien;

    @NotBlank(message = "Loại tài khoản không được để trống")
    @Pattern(regexp = "^(KhachHang|NhanVien|ChuSan)$",
            message = "Loại tài khoản phải là KhachHang, NhanVien hoặc ChuSan")
    @Column(name = "loai_tai_khoan")
    private String loaiTaiKhoan;

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(HoatDong|BiKhoa)$",
            message = "Trạng thái phải là HoatDong hoặc BiKhoa")
    @Column(name = "trang_thai")
    private String trangThai;

    @CreationTimestamp
    @Column(name = "ngay_tao", columnDefinition = "DATETIME2(3)")
    private LocalDateTime ngayTao;

    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat", columnDefinition = "DATETIME2(3)")
    private LocalDateTime ngayCapNhat;
}
