package com.example.fpoly_stadium.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@MappedSuperclass
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column
    private LocalDateTime ngayTao;

    @UpdateTimestamp
    @Column
    private LocalDateTime ngayCapNhat;

    @Column
    private Boolean trangThai;
}
