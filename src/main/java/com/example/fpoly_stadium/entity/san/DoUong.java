package com.example.fpoly_stadium.entity.san;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "do_uong")
public class DoUong extends CommonEntity {
    @Id
    private Integer id;
    @Column(name = "ten_do_uong")
    private String tenDoUong;
    @Column(name = "don_gia")
    private Double donGia;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "trang_thai")
private Integer trangThai;
}
