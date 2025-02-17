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
@Table(name = "user")
public class User extends CommonEntity {
    @Column
    private String userName;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String password;

}
