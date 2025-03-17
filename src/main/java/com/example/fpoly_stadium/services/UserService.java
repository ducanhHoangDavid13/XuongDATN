package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.entity.user.User;
import java.util.List;

public interface UserService {
    void encodeAllPasswords();
    void saveUser(User user);
    List<User> getAllUsers();
}
