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
@Table(name = "san_bong")
public class SanBong extends CommonEntity {
    @Column
    private String diaChi;

    @Column
    private String tenSan;

    @ManyToOne
    @JoinColumn(name = "id_loai_san")
    private LoaiSan loaiSan;

}
