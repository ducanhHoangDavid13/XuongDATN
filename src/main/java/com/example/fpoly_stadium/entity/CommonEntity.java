package com.example.fpoly_stadium.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(nullable = false, updatable = false)
//    private LocalDateTime ngayTao;

//    @Column(nullable = false)
//    private LocalDateTime ngayCapNhat;

//    @Column(nullable = false)
//    private Integer trangThai = 1; // 1: Hoạt động, 0: Không hoạt động

//    @PrePersist
//    protected void onCreate() {
//        ngayTao = LocalDateTime.now();
//        ngayCapNhat = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        ngayCapNhat = LocalDateTime.now();
//    }
}
