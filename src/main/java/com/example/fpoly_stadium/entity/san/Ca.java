package com.example.fpoly_stadium.entity.san;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "ca")
public class Ca extends CommonEntity {
    @Column
    private String tenCa;

    @Column
    private LocalDateTime thoiGianBatDau;

    @Column
    private LocalDateTime thoiGianKetThuc;
}
