package com.example.fpoly_stadium.entity.user;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "Khach_Hang")
public class KhachHang extends CommonEntity {
//    @Id
//    private Integer id;
    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "ho_va_ten")
    private String hoVaTen;

    @Column(name = "email")
    private String email;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;
}
