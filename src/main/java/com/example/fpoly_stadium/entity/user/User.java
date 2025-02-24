package com.example.fpoly_stadium.entity.user;

import com.example.fpoly_stadium.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "user")
public class User extends CommonEntity {
    @Column()
    @NotEmpty(message = "Ten Nhan Vat khong duoc de de trong")
    private String userName;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    @NotEmpty(message = "Mat khau khong duoc de trong")
    @Size(min = 6,max = 250)
    private String password;

}
