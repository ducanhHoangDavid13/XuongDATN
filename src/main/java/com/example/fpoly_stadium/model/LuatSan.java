package com.example.fpoly_stadium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LuatSan")
public class LuatSan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không vượt quá 255 ký tự")
    @Column(name = "tieu_de", nullable = false)
    private String tieuDe;

    @NotBlank(message = "Nội dung không được để trống")
    @Lob
    @Column(name = "noi_dung", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    @Column(name = "ngay_ban_hanh")
    private LocalDate ngayBanHanh;

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(HieuLuc|HetHieuLuc)$", message = "Trạng thái phải là HieuLuc hoặc HetHieuLuc")
    @Size(max = 20)
    @Column(name = "trang_thai")
    private String trangThai = "HieuLuc";

    @PrePersist
    public void prePersist() {
        if (ngayBanHanh == null) {
            ngayBanHanh = LocalDate.now();
        }
    }
}
