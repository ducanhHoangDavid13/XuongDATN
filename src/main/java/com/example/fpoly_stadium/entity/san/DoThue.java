package com.example.fpoly_stadium.entity.san;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name ="do_thue")
public class DoThue extends CommonEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    @Column(name = "don_gia")
    private Double donGia;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "ten_do_thue")
    private String tenDoThue;
@Column(name = "trang_thai")
    private Integer trangThai;
}
