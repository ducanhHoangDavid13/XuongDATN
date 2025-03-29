package com.example.fpoly_stadium.entity.DTO;

import com.example.fpoly_stadium.entity.user.KhachHang;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

@Data
public class KhachHangDTO {

    private Integer id;

    @NotBlank(message = "Vui lòng nhập mã khách hàng")
    @Pattern(regexp = "^KH[0-9]{4}$", message = "Mã khách hàng phải có định dạng KH và 4 số (VD: KH0001)")
    private String maKhachHang;

    @NotBlank(message = "Vui lòng nhập họ và tên")
    @Size(min = 2, max = 50, message = "Họ và tên phải từ 2-50 ký tự")
    private String hoVaTen;

    @NotBlank(message = "Vui lòng nhập email")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Vui lòng chọn giới tính")
    private Boolean gioiTinh;

    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b",
            message = "Số điện thoại phải bắt đầu bằng 84 hoặc 03, 05, 07, 08, 09 và có 10 số")
    private String soDienThoai;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matKhau;

    // Constructor mặc định
    public KhachHangDTO() {
        this.gioiTinh = true; // Mặc định là Nam
    }

    // Các phương thức chuyển đổi
    public KhachHang toEntity() {
        KhachHang entity = new KhachHang();
        BeanUtils.copyProperties(this, entity);
        if (StringUtils.hasText(this.matKhau)) {
            entity.setMatKhau(this.matKhau); // mã hóa mật khẩu trước khi lưu
        }
        return entity;
    }

    public static KhachHangDTO fromEntity(KhachHang entity) {
        KhachHangDTO dto = new KhachHangDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}