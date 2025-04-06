package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.model.DichVu;
import com.example.fpoly_stadium.model.LoaiDichVu; // Vẫn cần import Model
import com.example.fpoly_stadium.services.DichVuService;
import com.example.fpoly_stadium.services.LoaiDichVuService; // Vẫn cần inject Service này
// import com.example.fpoly_stadium.services.ChiTietHoaDonService; // Inject nếu cần check ràng buộc xóa
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/services")
public class AdminDichVuController {

    private final DichVuService dichVuService;
    private final LoaiDichVuService loaiDichVuService; // Vẫn inject để lấy danh sách 2 loại cố định
    // private final ChiTietHoaDonService chiTietHoaDonService;

    @Autowired
    public AdminDichVuController(DichVuService dichVuService,
                                 LoaiDichVuService loaiDichVuService
            /*, ChiTietHoaDonService chiTietHoaDonService */) {
        this.dichVuService = dichVuService;
        this.loaiDichVuService = loaiDichVuService;
        // this.chiTietHoaDonService = chiTietHoaDonService;
    }

    // Hiển thị danh sách dịch vụ
    @GetMapping
    public String listServices(Model model,
                               @ModelAttribute("successMessage") String successMessage,
                               @ModelAttribute("errorMessage") String errorMessage) {
        List<DichVu> dichVuList = dichVuService.getAllDichVu();
        model.addAttribute("dichVuList", dichVuList);
        if (successMessage != null && !successMessage.isEmpty()) model.addAttribute("successMessage", successMessage);
        if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
        return "admin/services/list";
    }

    // Hiển thị form thêm mới
    @GetMapping("/new")
    public String showCreateForm(Model model, @ModelAttribute("errorMessage") String errorMessage) {
        model.addAttribute("dichVu", new DichVu()); // Tạo đối tượng DichVu mới
        model.addAttribute("pageTitle", "Thêm Dịch Vụ Mới");
        model.addAttribute("isEdit", false);
        addServiceTypesToModel(model); // <<-- Gọi hàm helper để lấy 2 loại DV
        if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
        return "admin/services/form_dichvu"; // Trả về view form
    }

    // Xử lý thêm mới
    @PostMapping
    public String createService(@Valid @ModelAttribute("dichVu") DichVu dichVu,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm Dịch Vụ Mới");
            model.addAttribute("isEdit", false);
            addServiceTypesToModel(model); // <<-- Load lại loại DV nếu có lỗi
            return "admin/services/form_dichvu";
        }
        try {
            // Có thể thêm kiểm tra trùng tên dịch vụ ở đây hoặc trong service
            dichVuService.createDichVu(dichVu);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm dịch vụ '" + dichVu.getTenDichVu() + "' thành công!");
            return "redirect:/admin/services";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm dịch vụ: " + e.getMessage());
            // Gửi lại đối tượng để điền form khi có lỗi lúc tạo
            redirectAttributes.addFlashAttribute("dichVu", dichVu);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dichVu", bindingResult); // Gửi cả lỗi binding nếu có
            return "redirect:/admin/services/new";
        }
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes redirectAttributes,
                               @ModelAttribute("errorMessage") String errorMessage) {
        try {
            DichVu dichVu = dichVuService.getDichVuById(id);
            model.addAttribute("dichVu", dichVu);
            model.addAttribute("pageTitle", "Chỉnh Sửa Dịch Vụ (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addServiceTypesToModel(model); // <<-- Gọi hàm helper để lấy 2 loại DV
            if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
            return "admin/services/form_dichvu";
        } catch (RuntimeException e) { // Bắt lỗi từ getDichVuById (ví dụ: EntityNotFound)
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/services";
        }
    }

    // Xử lý cập nhật
    @PostMapping("/{id}")
    public String updateService(@PathVariable("id") Integer id,
                                @Valid @ModelAttribute("dichVu") DichVu dichVu,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh Sửa Dịch Vụ (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addServiceTypesToModel(model); // <<-- Load lại loại DV nếu có lỗi
            dichVu.setId(id); // Đảm bảo giữ ID khi có lỗi validation
            model.addAttribute("dichVu", dichVu); // Gửi lại đối tượng với lỗi
            return "admin/services/form_dichvu";
        }
        dichVu.setId(id); // Đảm bảo ID được set cho đối tượng cập nhật
        try {
            // Có thể thêm kiểm tra trùng tên (loại trừ chính nó) ở đây hoặc trong service
            dichVuService.updateDichVu(dichVu);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật dịch vụ '" + dichVu.getTenDichVu() + "' thành công!");
            return "redirect:/admin/services";
        } catch (RuntimeException e) { // Bắt lỗi từ service impl (vd: not found)
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Nếu lỗi khi cập nhật, quay lại form edit
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dichVu", bindingResult); // Gửi lỗi binding nếu có
            redirectAttributes.addFlashAttribute("dichVu", dichVu); // Gửi lại dữ liệu
            return "redirect:/admin/services/" + id + "/edit";
        } catch (Exception e) { // Bắt lỗi chung khác
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi không mong muốn khi cập nhật dịch vụ.");
            return "redirect:/admin/services/" + id + "/edit";
        }
    }

    // Xử lý xóa
    @PostMapping("/{id}/delete")
    public String deleteService(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            // *** KIỂM TRA RÀNG BUỘC TRONG SERVICE IMPL LÀ TỐT NHẤT ***
            // boolean isInUse = chiTietHoaDonService.isDichVuInUse(id);
            // if (isInUse) { ... }

            String tenDichVu = "";
            try {
                tenDichVu = dichVuService.getDichVuById(id).getTenDichVu(); // Lấy tên trước khi xóa
            } catch (RuntimeException ignored) { } // Bỏ qua nếu không tìm thấy để xóa

            dichVuService.deleteDichVu(id); // Service sẽ ném lỗi nếu không tìm thấy hoặc không xóa được
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa dịch vụ '" + tenDichVu + "' thành công!");
        } catch (RuntimeException e) { // Bắt lỗi chung từ service impl
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage()); // Hiển thị lỗi từ service
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi không mong muốn khi xóa dịch vụ.");
        }
        return "redirect:/admin/services";
    }

    // Hàm tiện ích thêm danh sách loại dịch vụ cố định vào model
    private void addServiceTypesToModel(Model model) {
        // Gọi service để lấy danh sách (chỉ có 2 loại cố định trong DB)
        List<LoaiDichVu> loaiDichVuList = loaiDichVuService.getAllLoaiDichVu();
        // Truyền danh sách này vào model để dropdown trong view sử dụng
        model.addAttribute("danhSachLoaiDichVu", loaiDichVuList);
    }
}