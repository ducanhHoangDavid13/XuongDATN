package com.example.fpoly_stadium.controller;
import com.example.fpoly_stadium.model.PhieuGiamGia;
import com.example.fpoly_stadium.services.PhieuGiamGiaService;
// Import các service/repository khác nếu cần kiểm tra ràng buộc
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/discounts")
public class AdminDiscountController {

    private final PhieuGiamGiaService phieuGiamGiaService;
    // Inject service khác nếu cần kiểm tra ràng buộc với PhieuGiamGiaApDung

    @Autowired
    public AdminDiscountController(PhieuGiamGiaService phieuGiamGiaService) {
        this.phieuGiamGiaService = phieuGiamGiaService;
    }

    // Hiển thị danh sách
    @GetMapping
    public String listDiscounts(Model model,
                                @ModelAttribute("successMessage") String successMessage,
                                @ModelAttribute("errorMessage") String errorMessage) {
        List<PhieuGiamGia> discountList = phieuGiamGiaService.getAllPhieuGiamGia();
        model.addAttribute("discountList", discountList);
        if (successMessage != null && !successMessage.isEmpty()) model.addAttribute("successMessage", successMessage);
        if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
        return "admin/discounts/list"; // Cần tạo view này
    }

    // Hiển thị form thêm mới
    @GetMapping("/new")
    public String showCreateForm(Model model, @ModelAttribute("errorMessage") String errorMessage) {
        model.addAttribute("phieuGiamGia", new PhieuGiamGia());
        model.addAttribute("pageTitle", "Thêm Phiếu Giảm Giá Mới");
        model.addAttribute("isEdit", false);
        addDiscountTypesAndStatusToModel(model); // Thêm loại giảm giá, trạng thái vào model
        if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
        return "admin/discounts/form"; // Cần tạo view này
    }

    // Xử lý thêm mới
    @PostMapping
    public String createDiscount(@Valid @ModelAttribute("phieuGiamGia") PhieuGiamGia phieuGiamGia,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        // Kiểm tra mã giảm giá đã tồn tại chưa
        if (phieuGiamGiaService.findByMaGiamGia(phieuGiamGia.getMaGiamGia()).isPresent()) { // Giả sử có phương thức này
            bindingResult.addError(new FieldError("phieuGiamGia", "maGiamGia", "Mã giảm giá này đã tồn tại."));
        }
        // Thêm các kiểm tra validation logic khác nếu cần (vd: giá trị giảm tối đa chỉ áp dụng cho loại %, ngày kết thúc > ngày bắt đầu)

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm Phiếu Giảm Giá Mới");
            model.addAttribute("isEdit", false);
            addDiscountTypesAndStatusToModel(model);
            return "admin/discounts/form";
        }
        try {
            phieuGiamGiaService.createPhieuGiamGia(phieuGiamGia);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm phiếu giảm giá '" + phieuGiamGia.getMaGiamGia() + "' thành công!");
            return "redirect:/admin/discounts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm phiếu giảm giá: " + e.getMessage());
            return "redirect:/admin/discounts/new";
        }
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes redirectAttributes,
                               @ModelAttribute("errorMessage") String errorMessage) {
        try {
            // Sử dụng Optional để xử lý trường hợp không tìm thấy
            PhieuGiamGia phieuGiamGia = phieuGiamGiaService.getPhieuGiamGiaById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Phiếu giảm giá với ID: " + id));

            model.addAttribute("phieuGiamGia", phieuGiamGia);
            model.addAttribute("pageTitle", "Chỉnh Sửa Phiếu Giảm Giá (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addDiscountTypesAndStatusToModel(model);
            if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
            return "admin/discounts/form";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/discounts";
        }
    }

    // Xử lý cập nhật
    @PostMapping("/{id}")
    public String updateDiscount(@PathVariable("id") Integer id,
                                 @Valid @ModelAttribute("phieuGiamGia") PhieuGiamGia phieuGiamGia,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // Kiểm tra mã giảm giá trùng (loại trừ chính nó)
        Optional<PhieuGiamGia> existingByCode = phieuGiamGiaService.findByMaGiamGia(phieuGiamGia.getMaGiamGia());
        if (existingByCode.isPresent() && !existingByCode.get().getId().equals(id)) {
            bindingResult.addError(new FieldError("phieuGiamGia", "maGiamGia", "Mã giảm giá này đã tồn tại cho phiếu khác."));
        }
        // Thêm các kiểm tra validation logic khác

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh Sửa Phiếu Giảm Giá (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addDiscountTypesAndStatusToModel(model);
            phieuGiamGia.setId(id); // Giữ ID
            model.addAttribute("phieuGiamGia", phieuGiamGia);
            return "admin/discounts/form";
        }
        phieuGiamGia.setId(id); // Đảm bảo ID đúng
        try {
            phieuGiamGiaService.updatePhieuGiamGia(phieuGiamGia);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật phiếu giảm giá '" + phieuGiamGia.getMaGiamGia() + "' thành công!");
            return "redirect:/admin/discounts";
        } catch (EntityNotFoundException e) { // Có thể service update ném lỗi này nếu ID không tồn tại
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/discounts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật phiếu giảm giá: " + e.getMessage());
            return "redirect:/admin/discounts/" + id + "/edit";
        }
    }

    // Xử lý xóa (Cẩn thận!)
    @PostMapping("/{id}/delete")
    public String deleteDiscount(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            // *** KIỂM TRA RÀNG BUỘC VỚI PhieuGiamGiaApDungService ***
            // boolean isInUse = phieuGiamGiaApDungService.isPhieuGiamGiaInUse(id); // Cần tạo service/method này
            // if(isInUse) {
            //     throw new DataIntegrityViolationException("Không thể xóa phiếu này vì đã được áp dụng cho hóa đơn.");
            // }

            String maGiamGia = "";
            Optional<PhieuGiamGia> optPhieu = phieuGiamGiaService.getPhieuGiamGiaById(id);
            if(optPhieu.isPresent()){
                maGiamGia = optPhieu.get().getMaGiamGia();
            } else {
                throw new EntityNotFoundException("Không tìm thấy Phiếu giảm giá với ID: " + id);
            }

            phieuGiamGiaService.deletePhieuGiamGia(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa phiếu giảm giá '" + maGiamGia + "' thành công!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa phiếu này vì đang có dữ liệu liên quan (đã được áp dụng).");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi không mong muốn khi xóa phiếu giảm giá: " + e.getMessage());
        }
        return "redirect:/admin/discounts";
    }

    // Hàm tiện ích
    private void addDiscountTypesAndStatusToModel(Model model) {
        model.addAttribute("discountTypes", List.of("PhanTram", "SoTien"));
        model.addAttribute("statuses", List.of("HoatDong", "HetHan", "TamDung", "HetLuot"));
    }
}
