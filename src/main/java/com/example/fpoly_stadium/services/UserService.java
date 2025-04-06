package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.Account;

public interface UserService {
    Account registerUser(Account user);
    Account findByUsername(String username);
}
