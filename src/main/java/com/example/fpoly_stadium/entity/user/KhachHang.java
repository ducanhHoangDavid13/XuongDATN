package com.example.fpoly_stadium.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "khach_hang")
public class KhachHang {
    @Column
    private String maKhachHang;

    @Column
    private String matKhau;

    @Column
    private String hoVaTen;

    @Column
    private String email;

    @Column
    private Boolean gioiTinh;

    @Column
    private String soDienThoai;
}
