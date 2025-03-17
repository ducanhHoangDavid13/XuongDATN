package com.example.fpoly_stadium.entity.hoaDon;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "hinh_thuc_thanh_toan")
public class HinhThucThanhToan extends CommonEntity {
    @Column
    private String hinhThucThanhToan;
    @Column
    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.0", message = "Số tiền phải lớn hơn hoặc bằng 0")
    private BigDecimal soTien;

}
