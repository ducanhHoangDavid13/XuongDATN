package com.example.fpoly_stadium.entity.hoaDon;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "phieu_giam_gia")
public class PhieuGiamGia extends CommonEntity {
    @Column
    private String maPhieuGiamGia;

    @Column
    private String tenPhieuGiamGia;

    @Column
    private Integer soLuong;

    @Column
    private Double mucGiam;

    @Column
    private Double giaTriToiDa;

    @Column
    private Boolean hinhThucGiamGia;

    @Column
    private Boolean doiTuongApDung;

    @Column
    private LocalDateTime ngayBatDau;

    @Column
    private LocalDateTime ngayKetThuc;

    @Column
    private String ghiChu;

    @Column
    private Double dieuKienSuDung;

}
