package com.example.fpoly_stadium.entity.user;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "role")
public class Role extends CommonEntity {
    @Column
    private String name;
}
