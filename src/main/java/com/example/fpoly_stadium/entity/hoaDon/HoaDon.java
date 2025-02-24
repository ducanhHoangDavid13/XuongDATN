package com.example.fpoly_stadium.entity.hoaDon;

import com.example.fpoly_stadium.entity.CommonEntity;
import com.example.fpoly_stadium.entity.user.KhachHang;
import com.example.fpoly_stadium.entity.user.NhanVien;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "hoa_don")
public class HoaDon extends CommonEntity {
    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @Column
    private String maHoaDon;

    @Column
    private Double tongTienSan;

    @Column
    private Double tienCoc;

    @Column
    private Double tongTien;

    @Column
    private String ghiChu;

    @Column
    private Boolean loai;
}
