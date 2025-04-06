package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    // JpaRepository cung cấp sẵn các phương thức CRUD cơ bản như:
    // save(), findById(), findAll(), deleteById(), existsById(), count(), ...

    // --- Các phương thức truy vấn tùy chỉnh (Spring Data JPA tự tạo dựa trên tên) ---

    // Tìm kiếm tài khoản dựa trên username. Trả về Optional để xử lý trường hợp không tìm thấy.
    Optional<Account> findByUsername(String username);

    // Tìm kiếm tài khoản dựa trên email.
    Optional<Account> findByEmail(String email);

    // Tìm kiếm tài khoản dựa trên số điện thoại.
    Optional<Account> findBySoDienThoai(String soDienThoai);

    // Tìm danh sách các tài khoản dựa trên loại tài khoản (ví dụ: 'KhachHang', 'NhanVien').
    List<Account> findByLoaiTaiKhoan(String loaiTaiKhoan);

    // Kiểm tra xem có tài khoản nào tồn tại với username đã cho hay không.
    boolean existsByUsername(String username);

    // Kiểm tra xem có tài khoản nào tồn tại với email đã cho hay không.
    boolean existsByEmail(String email);

    // Kiểm tra xem có tài khoản nào tồn tại với số điện thoại đã cho hay không.
    boolean existsBySoDienThoai(String soDienThoai);

    // Kiểm tra xem có tài khoản nào tồn tại với username đã cho, NGOẠI TRỪ tài khoản có ID cụ thể.
    // Hữu ích khi cập nhật thông tin tài khoản, tránh báo lỗi trùng username của chính tài khoản đó.
    boolean existsByUsernameAndIdNot(String username, Integer id);

    // Kiểm tra sự tồn tại của email, loại trừ ID cụ thể.
    boolean existsByEmailAndIdNot(String email, Integer id);

    // Kiểm tra sự tồn tại của số điện thoại, loại trừ ID cụ thể.
    boolean existsBySoDienThoaiAndIdNot(String soDienThoai, Integer id);
}
