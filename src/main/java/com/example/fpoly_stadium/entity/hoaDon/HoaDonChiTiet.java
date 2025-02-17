package com.example.fpoly_stadium.entity.hoaDon;

import com.example.fpoly_stadium.entity.CommonEntity;
import com.example.fpoly_stadium.entity.san.SanCa;
import com.example.fpoly_stadium.entity.user.NhanVien;
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
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet extends CommonEntity {
    @ManyToOne
    @JoinColumn(name = "id_san_ca")
    private SanCa sanCa;

    @ManyToOne
    @JoinColumn(name = "id_phieu_giam_gia")
    private PhieuGiamGia phieuGiamGia;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;

    @Column
    private String maHoaDonChiTiet;

    @Column
    private Integer ngayDenSan;

    @Column
    private Integer tienGiamGia;

    @Column
    private String ghiChu;

}
