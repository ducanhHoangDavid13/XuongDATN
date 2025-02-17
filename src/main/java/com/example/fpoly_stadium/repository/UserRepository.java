package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
