package com.example.fpoly_stadium.entity.san;

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
@Table(name = "san_ca")
public class SanCa extends CommonEntity {
    @ManyToOne
    @JoinColumn(name = "id_ca")
    private Ca ca;

    @ManyToOne
    @JoinColumn(name = "ngay_trong_tuan")
    private NgayTrongTuan ngayTrongTuan;

    @ManyToOne
    @JoinColumn(name = "id_san_bong")
    private SanBong sanBong;

    @Column
    private Double gia;

}
