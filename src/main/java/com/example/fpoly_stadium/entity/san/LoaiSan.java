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
@Table(name = "loai_san")
public class LoaiSan extends CommonEntity {
    @Column
    private String tenLoaiSan;
}
