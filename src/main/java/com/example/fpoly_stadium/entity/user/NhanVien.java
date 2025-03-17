package com.example.fpoly_stadium.entity.user;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "nhan_vien")
public class NhanVien extends CommonEntity {
<<<<<<< HEAD
    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ten_nhan_vien")
    private String tenNhanVien;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "email")
=======

    @Column(name = "ma_nhan_vien", unique = true, nullable = false)
    private String maNhanVien;

    @Column(name = "ten_nhan_vien", nullable = false)
    private String tenNhanVien;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "email", unique = true)
>>>>>>> f8ee6624fa0591064e66b5c48943609c2be196b4
    private String email;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "dia_chi")
    private String diaChi;
<<<<<<< HEAD
}
=======

    @Column(name = "trang_thai")
    private Integer trangThai;
}
>>>>>>> f8ee6624fa0591064e66b5c48943609c2be196b4
