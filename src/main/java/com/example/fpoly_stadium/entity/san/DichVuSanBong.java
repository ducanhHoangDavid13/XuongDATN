package com.example.fpoly_stadium.entity.san;

import com.example.fpoly_stadium.entity.CommonEntity;
import com.example.fpoly_stadium.entity.hoaDon.HoaDonChiTiet;
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
@Table(name = "dich_vu_san_bong")
public class DichVuSanBong extends CommonEntity {
    @ManyToOne
    @JoinColumn(name = "id_do_uong")
    private DoUong doUong;

    @ManyToOne
    @JoinColumn(name = "id_do_thue")
    private DoThue doThue;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don_chi_tiet")
    private HoaDonChiTiet hoaDonChiTiet;

    @Column
    private Double tongTien;
}
