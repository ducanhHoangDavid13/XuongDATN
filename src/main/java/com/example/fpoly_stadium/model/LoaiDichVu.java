package com.example.fpoly_stadium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LoaiDichVu")
public class LoaiDichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên loại dịch vụ không được để trống")
    @Size(max = 50, message = "Tên loại dịch vụ không vượt quá 50 ký tự")
    @Column(name = "ten_loai", nullable = false, unique = true)
    private String tenLoai;
}
