package com.example.fpoly_stadium.entity.san;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "loai_san")
public class LoaiSan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_loai_san")
    private String tenLoaiSan;

    @Column(name = "trang_thai")
    private Integer trangThai;
}

