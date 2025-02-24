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
