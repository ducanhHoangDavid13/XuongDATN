package com.example.fpoly_stadium.entity.hoaDon;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "phieu_giam_gia")
public class PhieuGiamGia extends CommonEntity {

    @Column(name = "ma_phieu_giam_gia")
    private String maPhieuGiamGia;

    @Column(name = "ten_phieu_giam_gia")
    private String tenPhieuGiamGia;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "muc_giam")
    private Float mucGiam;

    @Column(name = "dieu_kien_su_dung")
    private Float dieuKienApDung;

    @Column(name = "gia_tri_toi_da")
    private Float giaTriToiDa;

    @Column(name = "hinh_thuc_giam_gia")
    private Boolean hinhThucGiamGia;

    @Column(name = "doi_tuong_ap_dung")
    private String doiTuongApDung;

    @Column(name = "ngay_bat_dau")
    @Temporal(TemporalType.DATE)
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    @Temporal(TemporalType.DATE)
    private Date ngayKetThuc;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private String trangThai;
}