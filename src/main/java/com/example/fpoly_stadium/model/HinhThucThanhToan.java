package com.example.fpoly_stadium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HinhThucThanhToan")
public class HinhThucThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên hình thức không được để trống")
    @Size(max = 50, message = "Tên hình thức không vượt quá 50 ký tự")
    @Column(name = "ten_hinh_thuc", nullable = false, unique = true)
    private String tenHinhThuc;
}
