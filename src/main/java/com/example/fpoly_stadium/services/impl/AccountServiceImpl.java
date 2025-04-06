package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.Account;
import com.example.fpoly_stadium.repository.AccountRepository;
import com.example.fpoly_stadium.services.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Account> getAllAccounts() {
        // Repository đã có sẵn findAll()
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAccountsByRole(String role) {
        // Repository đã có findByLoaiTaiKhoan()
        return accountRepository.findByLoaiTaiKhoan(role);
    }

    @Override
    public Optional<Account> findAccountById(Integer id) {
        // Repository đã có sẵn findById()
        return accountRepository.findById(id);
    }

    @Override
    public Account getAccountById(Integer id) {
        // Xử lý Optional trả về từ findById()
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản với ID: " + id));
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        // Repository đã có findByUsername()
        return accountRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public Account createAccount(Account account, String rawPassword) {
        // Kiểm tra trùng lặp bằng các phương thức exists... từ repository
        if (accountRepository.existsByUsername(account.getUsername())) { // Gọi thẳng repository
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại.");
        }
        if (StringUtils.hasText(account.getEmail()) && accountRepository.existsByEmail(account.getEmail())) { // Gọi thẳng repository
            throw new IllegalArgumentException("Email đã tồn tại.");
        }
        if (StringUtils.hasText(account.getSoDienThoai()) && accountRepository.existsBySoDienThoai(account.getSoDienThoai())) { // Gọi thẳng repository
            throw new IllegalArgumentException("Số điện thoại đã tồn tại.");
        }

        account.setPasswordHash(passwordEncoder.encode(rawPassword));
        if (!StringUtils.hasText(account.getTrangThai())) {
            account.setTrangThai("HoatDong");
        }
        // Repository đã có sẵn save()
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account updateAccount(Account accountDetails, String rawPassword) {
        Account existingAccount = getAccountById(accountDetails.getId()); // Lấy account đang tồn tại

        // Kiểm tra trùng lặp bằng các phương thức exists...AndIdNot từ repository
        if (accountRepository.existsByUsernameAndIdNot(accountDetails.getUsername(), accountDetails.getId())) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại cho tài khoản khác.");
        }
        if (StringUtils.hasText(accountDetails.getEmail()) && accountRepository.existsByEmailAndIdNot(accountDetails.getEmail(), accountDetails.getId())) {
            throw new IllegalArgumentException("Email đã tồn tại cho tài khoản khác.");
        }
        if (StringUtils.hasText(accountDetails.getSoDienThoai()) && accountRepository.existsBySoDienThoaiAndIdNot(accountDetails.getSoDienThoai(), accountDetails.getId())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại cho tài khoản khác.");
        }

        // Cập nhật các trường...
        existingAccount.setUsername(accountDetails.getUsername());
        existingAccount.setEmail(accountDetails.getEmail());
        existingAccount.setHoTen(accountDetails.getHoTen());
        existingAccount.setSoDienThoai(accountDetails.getSoDienThoai());
        existingAccount.setDiaChi(accountDetails.getDiaChi());
        existingAccount.setAnhDaiDien(accountDetails.getAnhDaiDien());
        existingAccount.setLoaiTaiKhoan(accountDetails.getLoaiTaiKhoan());
        existingAccount.setTrangThai(accountDetails.getTrangThai());

        if (StringUtils.hasText(rawPassword)) {
            existingAccount.setPasswordHash(passwordEncoder.encode(rawPassword));
        }
        // Repository đã có sẵn save()
        return accountRepository.save(existingAccount);
    }

    @Override
    @Transactional
    public Account updateProfile(Account accountDetails) {
        Account existingAccount = getAccountById(accountDetails.getId());

        // Kiểm tra trùng lặp nếu email/sđt thay đổi
        if (StringUtils.hasText(accountDetails.getEmail()) && !accountDetails.getEmail().equals(existingAccount.getEmail()) && accountRepository.existsByEmailAndIdNot(accountDetails.getEmail(), accountDetails.getId())) {
            throw new IllegalArgumentException("Email đã tồn tại cho tài khoản khác.");
        }
        if (StringUtils.hasText(accountDetails.getSoDienThoai()) && !accountDetails.getSoDienThoai().equals(existingAccount.getSoDienThoai()) && accountRepository.existsBySoDienThoaiAndIdNot(accountDetails.getSoDienThoai(), accountDetails.getId())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại cho tài khoản khác.");
        }

        // Cập nhật thông tin profile
        existingAccount.setEmail(accountDetails.getEmail());
        existingAccount.setHoTen(accountDetails.getHoTen());
        existingAccount.setSoDienThoai(accountDetails.getSoDienThoai());
        existingAccount.setDiaChi(accountDetails.getDiaChi());
        existingAccount.setAnhDaiDien(accountDetails.getAnhDaiDien());

        // Repository đã có sẵn save()
        return accountRepository.save(existingAccount);
    }

    @Override
    @Transactional
    public Account toggleAccountStatus(Integer id) {
        Account account = getAccountById(id);
        account.setTrangThai("HoatDong".equals(account.getTrangThai()) ? "BiKhoa" : "HoatDong");
        // Repository đã có sẵn save()
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(Integer id) {
        // Repository đã có sẵn existsById()
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy tài khoản với ID: " + id);
        }
        // TODO: Kiểm tra ràng buộc khóa ngoại trước khi xóa
        try {
            // Repository đã có sẵn deleteById()
            accountRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Không thể xóa tài khoản này do có dữ liệu liên quan.", e);
        }
    }

    // --- Các phương thức kiểm tra tồn tại ---
    // Gọi trực tiếp các phương thức tương ứng từ repository
    @Override public boolean existsByUsername(String username) { return accountRepository.existsByUsername(username); }
    @Override public boolean existsByEmail(String email) { return StringUtils.hasText(email) && accountRepository.existsByEmail(email); }
    @Override public boolean existsBySoDienThoai(String soDienThoai) { return StringUtils.hasText(soDienThoai) && accountRepository.existsBySoDienThoai(soDienThoai); }
    @Override public boolean existsByUsernameAndIdNot(String username, Integer id) { return accountRepository.existsByUsernameAndIdNot(username, id); }
    @Override public boolean existsByEmailAndIdNot(String email, Integer id) { return StringUtils.hasText(email) && accountRepository.existsByEmailAndIdNot(email, id); }
    @Override public boolean existsBySoDienThoaiAndIdNot(String soDienThoai, Integer id) { return StringUtils.hasText(soDienThoai) && accountRepository.existsBySoDienThoaiAndIdNot(soDienThoai, id); }

}