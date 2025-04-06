package com.example.fpoly_stadium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ThamSoHeThong")
public class ThamSoHeThong {
    @Id
    @Column(name = "ma_tham_so", length = 100)
    private String maThamSo;

    @NotBlank(message = "Giá trị không được để trống")
    @Size(max = 255, message = "Giá trị không vượt quá 255 ký tự")
    @Column(name = "gia_tri", nullable = false)
    private String giaTri;

    @Size(max = 500, message = "Mô tả không vượt quá 500 ký tự")
    @Column(name = "mo_ta")
    private String moTa;

    @Pattern(regexp = "^(SoNguyen|SoThuc|Chuoi|ThoiGianPhut|Boolean)$",
            message = "Kiểu dữ liệu phải là SoNguyen, SoThuc, Chuoi, ThoiGianPhut hoặc Boolean")
    @Size(max = 50, message = "Kiểu dữ liệu không vượt quá 50 ký tự")
    @Column(name = "kieu_du_lieu")
    private String kieuDuLieu;
}
