package com.example.fpoly_stadium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SanBong")
public class SanBong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên sân không được để trống")
    @Size(max = 100, message = "Tên sân không vượt quá 100 ký tự")
    @Column(name = "ten_san", nullable = false)
    private String tenSan;

    @NotNull(message = "Loại sân không được để trống")
    @ManyToOne
    @JoinColumn(name = "loai_san_id", nullable = false)
    private LoaiSan loaiSan;

    @Size(max = 255, message = "Địa chỉ không vượt quá 255 ký tự")
    @Column(name = "dia_chi")
    private String diaChi;

    // Sử dụng kiểu Short để ánh xạ SMALLINT
    @Column(name = "suc_chua")
    private Short sucChua;

    @Size(max = 255, message = "Ảnh đại diện không vượt quá 255 ký tự")
    @Column(name = "anh_dai_dien")
    private String anhDaiDien;

    @Size(max = 1000, message = "Tiện ích không vượt quá 1000 ký tự")
    @Column(name = "tien_ich")
    private String tienIch;

    @Size(max = 1000, message = "Mô tả không vượt quá 1000 ký tự")
    @Column(name = "mo_ta")
    private String moTa;

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(HoatDong|BaoTri|TamNgung)$", message = "Trạng thái phải là HoatDong, BaoTri hoặc TamNgung")
    @Column(name = "trang_thai", nullable = false)
    private String trangThai;
}
