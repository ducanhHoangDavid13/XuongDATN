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
@Table(name = "do_thue")
public class DoThue extends CommonEntity {
    @Column
    private Double donGia;

    @Column
    private Integer soLuong;

    @Column
    private String tenDoThue;

}
