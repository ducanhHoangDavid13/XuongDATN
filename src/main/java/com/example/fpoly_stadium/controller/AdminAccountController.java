package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.model.Account;
import com.example.fpoly_stadium.services.AccountService;
import jakarta.persistence.EntityNotFoundException; // Import
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; // Import để bắt lỗi ràng buộc
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/accounts")
public class AdminAccountController {

    private final AccountService accountService;

    @Autowired
    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Hiển thị danh sách (Thêm nhận thông báo)
    @GetMapping
    public String listAccounts(Model model,
                               @RequestParam(required = false) String role,
                               @ModelAttribute("successMessage") String successMessage, // Nhận từ redirect
                               @ModelAttribute("errorMessage") String errorMessage) { // Nhận từ redirect
        List<Account> accountList;
        if (role != null && !role.isEmpty()) {
            accountList = accountService.getAccountsByRole(role);
            model.addAttribute("selectedRole", role);
        } else {
            accountList = accountService.getAllAccounts();
        }
        model.addAttribute("accountList", accountList);

        // Truyền thông báo vào model để view hiển thị
        if (successMessage != null && !successMessage.isEmpty()) model.addAttribute("successMessage", successMessage);
        if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);

        return "admin/accounts/list";
    }

    // Hiển thị form thêm mới (Thêm nhận thông báo nếu redirect về)
    @GetMapping("/new")
    public String showCreateForm(Model model,
                                 @ModelAttribute("errorMessage") String errorMessage) { // Nhận từ redirect nếu có lỗi khi tạo
        model.addAttribute("account", new Account());
        model.addAttribute("pageTitle", "Thêm Tài Khoản Mới");
        model.addAttribute("isEdit", false);
        addRoleAndStatusToModel(model);
        if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
        return "admin/accounts/form";
    }


    // Xử lý thêm mới (Gửi thông báo qua RedirectAttributes)
    @PostMapping
    public String createAccount(@Valid @ModelAttribute("account") Account account,
                                BindingResult bindingResult,
                                @RequestParam("rawPassword") String rawPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                RedirectAttributes redirectAttributes, // Dùng để gửi thông báo
                                Model model) {

        // --- Kiểm tra lỗi validation như cũ ---
        if (rawPassword == null || rawPassword.isEmpty()) {
            bindingResult.addError(new FieldError("account", "passwordHash", "Mật khẩu không được để trống"));
        } else if (!rawPassword.equals(confirmPassword)) {
            bindingResult.addError(new FieldError("account", "passwordHash", "Mật khẩu nhập lại không khớp"));
        }
        if (accountService.existsByUsername(account.getUsername())) {
            bindingResult.addError(new FieldError("account", "username", "Tên đăng nhập đã tồn tại"));
        }
        if (account.getEmail() != null && !account.getEmail().isEmpty() && accountService.existsByEmail(account.getEmail())) {
            bindingResult.addError(new FieldError("account", "email", "Email đã tồn tại"));
        }
        if (account.getSoDienThoai() != null && !account.getSoDienThoai().isEmpty() && accountService.existsBySoDienThoai(account.getSoDienThoai())) {
            bindingResult.addError(new FieldError("account", "soDienThoai", "Số điện thoại đã tồn tại"));
        }
        // --- Kết thúc kiểm tra lỗi ---

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm Tài Khoản Mới");
            model.addAttribute("isEdit", false);
            addRoleAndStatusToModel(model);
            return "admin/accounts/form";
        }

        try {
            accountService.createAccount(account, rawPassword);
            // *** GỬI THÔNG BÁO THÀNH CÔNG ***
            redirectAttributes.addFlashAttribute("successMessage", "Đã thêm tài khoản '" + account.getUsername() + "' thành công!");
            return "redirect:/admin/accounts";
        } catch (IllegalArgumentException e) {
            model.addAttribute("pageTitle", "Thêm Tài Khoản Mới");
            model.addAttribute("isEdit", false);
            addRoleAndStatusToModel(model);
            // *** HIỂN THỊ LỖI TRỰC TIẾP TRÊN FORM ***
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/accounts/form";
        } catch (Exception e) {
            // *** GỬI THÔNG BÁO LỖI CHUNG QUA REDIRECT ***
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi không mong muốn khi thêm tài khoản: " + e.getMessage());
            return "redirect:/admin/accounts/new"; // Quay lại trang new với thông báo lỗi
        }
    }

    // Hiển thị form chỉnh sửa (Thêm nhận thông báo)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes redirectAttributes,
                               @ModelAttribute("errorMessage") String errorMessage) { // Nhận từ redirect
        try {
            Account account = accountService.getAccountById(id);
            account.setPasswordHash(null);
            model.addAttribute("account", account);
            model.addAttribute("pageTitle", "Chỉnh Sửa Tài Khoản (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addRoleAndStatusToModel(model);
            if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage); // Hiển thị nếu có
            return "admin/accounts/form";
        } catch (EntityNotFoundException e) {
            // *** GỬI THÔNG BÁO LỖI QUA REDIRECT ***
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/accounts";
        }
    }

    // Xử lý cập nhật (Gửi thông báo)
    @PostMapping("/{id}")
    public String updateAccount(@PathVariable("id") Integer id,
                                @Valid @ModelAttribute("account") Account account,
                                BindingResult bindingResult,
                                @RequestParam(value = "rawPassword", required = false) String rawPassword,
                                @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
                                RedirectAttributes redirectAttributes, // Dùng để gửi thông báo
                                Model model) {

        // --- Kiểm tra lỗi validation như cũ ---
        if (rawPassword != null && !rawPassword.isEmpty() && !rawPassword.equals(confirmPassword)) {
            bindingResult.addError(new FieldError("account", "passwordHash", "Mật khẩu nhập lại không khớp"));
        }
        if (accountService.existsByUsernameAndIdNot(account.getUsername(), id)) {
            bindingResult.addError(new FieldError("account", "username", "Tên đăng nhập đã tồn tại cho tài khoản khác."));
        }
        if (account.getEmail() != null && !account.getEmail().isEmpty() && accountService.existsByEmailAndIdNot(account.getEmail(), id)) {
            bindingResult.addError(new FieldError("account", "email", "Email đã tồn tại cho tài khoản khác."));
        }
        if (account.getSoDienThoai() != null && !account.getSoDienThoai().isEmpty() && accountService.existsBySoDienThoaiAndIdNot(account.getSoDienThoai(), id)) {
            bindingResult.addError(new FieldError("account", "soDienThoai", "Số điện thoại đã tồn tại cho tài khoản khác."));
        }
        // --- Kết thúc kiểm tra lỗi ---

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh Sửa Tài Khoản (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addRoleAndStatusToModel(model);
            account.setId(id);
            model.addAttribute("account", account);
            return "admin/accounts/form";
        }

        account.setId(id);

        try {
            accountService.updateAccount(account, rawPassword);
            // *** GỬI THÔNG BÁO THÀNH CÔNG ***
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật tài khoản '" + account.getUsername() + "' thành công!");
            return "redirect:/admin/accounts";
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            model.addAttribute("pageTitle", "Chỉnh Sửa Tài Khoản (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addRoleAndStatusToModel(model);
            // *** HIỂN THỊ LỖI TRỰC TIẾP TRÊN FORM ***
            model.addAttribute("errorMessage", e.getMessage());
            account.setId(id);
            model.addAttribute("account", account);
            return "admin/accounts/form";
        } catch (Exception e) {
            // *** GỬI THÔNG BÁO LỖI CHUNG QUA REDIRECT ***
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi không mong muốn khi cập nhật tài khoản: "+e.getMessage());
            return "redirect:/admin/accounts/" + id + "/edit"; // Quay lại trang edit
        }
    }

    // Thay đổi trạng thái (Gửi thông báo)
    @PostMapping("/{id}/toggle-status")
    public String toggleStatus(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (id == 1) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể thay đổi trạng thái tài khoản quản trị chính!");
            return "redirect:/admin/accounts";
        }
        try {
            Account updatedAccount = accountService.toggleAccountStatus(id);
            String statusMsg = "HoatDong".equals(updatedAccount.getTrangThai()) ? "mở khóa" : "khóa";
            // *** GỬI THÔNG BÁO THÀNH CÔNG ***
            redirectAttributes.addFlashAttribute("successMessage", "Đã " + statusMsg + " tài khoản '" + updatedAccount.getUsername() + "' thành công!");
        } catch (EntityNotFoundException e) {
            // *** GỬI THÔNG BÁO LỖI KHÔNG TÌM THẤY ***
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            // *** GỬI THÔNG BÁO LỖI CHUNG ***
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thay đổi trạng thái tài khoản: "+e.getMessage());
        }
        return "redirect:/admin/accounts";
    }

    // Xử lý xóa (Gửi thông báo) - Hãy cẩn thận với chức năng này
    @PostMapping("/{id}/delete")
    public String deleteAccount(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (id == 1) { // Không cho xóa admin chính
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa tài khoản quản trị chính!");
            return "redirect:/admin/accounts";
        }
        return "redirect:/admin/accounts";
    }


    // Hàm tiện ích
    private void addRoleAndStatusToModel(Model model) {
        model.addAttribute("roles", List.of("KhachHang", "NhanVien", "ChuSan"));
        model.addAttribute("statuses", List.of("HoatDong", "BiKhoa"));
    }
}
