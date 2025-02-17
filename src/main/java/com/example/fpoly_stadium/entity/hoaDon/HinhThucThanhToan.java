package com.example.fpoly_stadium.entity.hoaDon;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "hinh_thuc_thanh_toan")
public class HinhThucThanhToan extends CommonEntity {
    @ManyToOne
    @JoinColumn(name = "id_hoa_don_chi_tiet")
    private HoaDonChiTiet hoaDonChiTiet;

    @Column
    private String hinhThucThanhToan;

    @Column
    private Double soTien;

}
