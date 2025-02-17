package com.example.fpoly_stadium.entity.user;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "nhan_vien")
public class NhanVien extends CommonEntity {
    @Column
    private String maNhanVien;

    @Column
    private String tenNhanVien;

    @Column
    private String matKhau;

    @Column
    private String email;

    @Column
    private Boolean gioiTinh;

    @Column
    private String sdt;

    @Column
    private String diaChi;
}
