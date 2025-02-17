package com.example.fpoly_stadium.entity.san;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "do_uong")
public class DoUong extends CommonEntity {
    @Column
    private String tenDoUong;

    @Column
    private Double donGia;

    @Column
    private Integer soLuong;

}
