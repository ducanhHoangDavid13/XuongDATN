package com.example.fpoly_stadium.model;
import com.example.fpoly_stadium.model.LoaiDichVu;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DichVu")
public class DichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Quan hệ với LoaiDichVu: không null, bắt buộc phải có loại dịch vụ
    @NotNull(message = "Loại dịch vụ không được để trống")
    @ManyToOne
    @JoinColumn(name = "loai_dich_vu_id", nullable = false)
    private LoaiDichVu loaiDichVu;

    // Tên dịch vụ: bắt buộc, tối đa 100 ký tự
    @NotBlank(message = "Tên dịch vụ không được để trống")
    @Size(max = 100, message = "Tên dịch vụ không vượt quá 100 ký tự")
    @Column(name = "ten_dich_vu", nullable = false)
    private String tenDichVu;

    // Đơn vị tính: mặc định là "Cái"
    @Size(max = 20, message = "Đơn vị tính không vượt quá 20 ký tự")
    @Column(name = "don_vi_tinh")
    private String donViTinh = "Cái";

    // Giá: không được null và phải >= 0, với precision 10 và scale 2
    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá phải lớn hơn hoặc bằng 0")
    @Column(name = "gia", nullable = false, precision = 10, scale = 2)
    private BigDecimal gia;

    // Số lượng tồn kho: có thể null, nếu có thì phải >= 0
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
    @Column(name = "so_luong_ton_kho")
    private Integer soLuongTonKho;

    // Ảnh minh họa: tối đa 255 ký tự
    @Size(max = 255, message = "Ảnh minh họa không vượt quá 255 ký tự")
    @Column(name = "anh_minh_hoa")
    private String anhMinhHoa;

    // Mô tả: tối đa 500 ký tự
    @Size(max = 500, message = "Mô tả không vượt quá 500 ký tự")
    @Column(name = "mo_ta")
    private String moTa;

    // Trạng thái: bắt buộc, chỉ chấp nhận các giá trị "DangKinhDoanh", "NgungKinhDoanh", "TamHet"
    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(DangKinhDoanh|NgungKinhDoanh|TamHet)$", message = "Trạng thái phải là DangKinhDoanh, NgungKinhDoanh hoặc TamHet")
    @Column(name = "trang_thai", nullable = false)
    private String trangThai = "DangKinhDoanh";

    @PrePersist
    public void prePersist() {
        if (donViTinh == null) {
            donViTinh = "Cái";
        }
        if (trangThai == null) {
            trangThai = "DangKinhDoanh";
        }
    }
}
