package com.example.fpoly_stadium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LoaiSan")
public class LoaiSan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên loại sân không được để trống")
    @Size(max = 50, message = "Tên loại sân không vượt quá 50 ký tự")
    @Column(name = "ten_loai_san", nullable = false, unique = true)
    private String tenLoaiSan;

    @Size(max = 500, message = "Mô tả không vượt quá 500 ký tự")
    @Column(name = "mo_ta")
    private String moTa;
}
