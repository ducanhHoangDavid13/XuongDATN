package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAllAccounts();
    List<Account> getAccountsByRole(String role); // Lọc theo vai trò
    Optional<Account> findAccountById(Integer id); // Trả về Optional để xử lý null an toàn hơn
    Account getAccountById(Integer id); // Vẫn giữ phương thức này nếu cần ném Exception
    Optional<Account> findByUsername(String username); // Trả về Optional
    // Account findByUsernameOrThrow(String username); // Có thể thêm phương thức này nếu cần

    // Chú ý tham số rawPassword
    Account createAccount(Account account, String rawPassword);
    Account updateAccount(Account account, String rawPassword); // Cập nhật cả thông tin và pass (nếu có)
    Account updateProfile(Account account); // Chỉ cập nhật thông tin profile (ko gồm pass, role, status)

    Account toggleAccountStatus(Integer id);
    void deleteAccount(Integer id); // Xem xét kỹ việc xóa cứng

    // Kiểm tra tồn tại
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsBySoDienThoai(String soDienThoai);
    boolean existsByUsernameAndIdNot(String username, Integer id);
    boolean existsByEmailAndIdNot(String email, Integer id);
    boolean existsBySoDienThoaiAndIdNot(String soDienThoai, Integer id);
}
