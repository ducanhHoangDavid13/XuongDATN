package com.example.fpoly_stadium.controller;
import com.example.fpoly_stadium.model.LoaiSan;
import com.example.fpoly_stadium.model.SanBong;
import com.example.fpoly_stadium.services.LoaiSanService; // Cần tạo service này
import com.example.fpoly_stadium.services.SanBongService; // Cần tạo service này
// import com.example.fpoly_stadium.services.LichDatService; // Cần inject nếu kiểm tra ràng buộc khi xóa
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
@RequestMapping("/admin/pitches")
public class AdminSanBongController {

    private final SanBongService sanBongService;
    private final LoaiSanService loaiSanService;
    // private final LichDatService lichDatService; // Để kiểm tra khi xóa

    @Autowired
    public AdminSanBongController(SanBongService sanBongService,
                                  LoaiSanService loaiSanService
            /*, LichDatService lichDatService */) {
        this.sanBongService = sanBongService;
        this.loaiSanService = loaiSanService;
        // this.lichDatService = lichDatService;
    }

    // Hiển thị danh sách sân bóng
    @GetMapping
    public String listPitches(Model model,
                              @ModelAttribute("successMessage") String successMessage,
                              @ModelAttribute("errorMessage") String errorMessage) {
        List<SanBong> pitchList = sanBongService.getAllSanBong(); // Giả sử có phương thức này
        model.addAttribute("pitchList", pitchList);
        if (successMessage != null && !successMessage.isEmpty()) model.addAttribute("successMessage", successMessage);
        if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
        return "admin/pitches/list";
    }

    // Hiển thị form thêm mới
    @GetMapping("/new")
    public String showCreateForm(Model model, @ModelAttribute("errorMessage") String errorMessage) {
        model.addAttribute("sanBong", new SanBong());
        model.addAttribute("pageTitle", "Thêm Sân Bóng Mới");
        model.addAttribute("isEdit", false);
        addPitchTypesAndStatusToModel(model); // Load loại sân và trạng thái
        if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
        return "admin/pitches/form";
    }

    // Xử lý thêm mới
    @PostMapping
    public String createPitch(@Valid @ModelAttribute("sanBong") SanBong sanBong,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm Sân Bóng Mới");
            model.addAttribute("isEdit", false);
            addPitchTypesAndStatusToModel(model);
            return "admin/pitches/form";
        }
        try {
            // Kiểm tra trùng tên sân nếu cần
            // if(sanBongService.existsByTenSan(sanBong.getTenSan())) { ... }
            sanBongService.createSanBong(sanBong); // Giả sử có phương thức này
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sân bóng '" + sanBong.getTenSan() + "' thành công!");
            return "redirect:/admin/pitches";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm sân bóng: " + e.getMessage());
            return "redirect:/admin/pitches/new";
        }
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes redirectAttributes,
                               @ModelAttribute("errorMessage") String errorMessage) {
        try {
            SanBong sanBong = sanBongService.getSanBongById(id); // Giả sử có phương thức này
            model.addAttribute("sanBong", sanBong);
            model.addAttribute("pageTitle", "Chỉnh Sửa Sân Bóng (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addPitchTypesAndStatusToModel(model);
            if (errorMessage != null && !errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);
            return "admin/pitches/form";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/pitches";
        }
    }

    // Xử lý cập nhật
    @PostMapping("/{id}")
    public String updatePitch(@PathVariable("id") Integer id,
                              @Valid @ModelAttribute("sanBong") SanBong sanBong,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh Sửa Sân Bóng (ID: " + id + ")");
            model.addAttribute("isEdit", true);
            addPitchTypesAndStatusToModel(model);
            sanBong.setId(id);
            model.addAttribute("sanBong", sanBong);
            return "admin/pitches/form";
        }
        sanBong.setId(id);
        try {
            // Kiểm tra trùng tên sân (loại trừ chính nó) nếu cần
            // if(sanBongService.existsByTenSanAndIdNot(sanBong.getTenSan(), id)) { ... }
            sanBongService.updateSanBong(sanBong); // Giả sử có phương thức này
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sân bóng '" + sanBong.getTenSan() + "' thành công!");
            return "redirect:/admin/pitches";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/pitches";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật sân bóng: " + e.getMessage());
            return "redirect:/admin/pitches/" + id + "/edit";
        }
    }

    // Xử lý xóa (Cẩn thận!)
    @PostMapping("/{id}/delete")
    public String deletePitch(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            // *** KIỂM TRA RÀNG BUỘC VỚI LỊCH ĐẶT ***
            // boolean isInUse = lichDatService.existsBySanBongId(id);
            // if (isInUse) {
            //     throw new DataIntegrityViolationException("Không thể xóa sân này vì đang có Lịch Đặt liên quan.");
            // }

            String tenSan = sanBongService.getSanBongById(id).getTenSan();
            sanBongService.deleteSanBong(id); // Giả sử có phương thức này
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sân bóng '" + tenSan + "' thành công!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sân này vì đang có dữ liệu liên quan (ví dụ: Lịch Đặt).");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi không mong muốn khi xóa sân bóng: " + e.getMessage());
        }
        return "redirect:/admin/pitches";
    }


    // Hàm tiện ích
    private void addPitchTypesAndStatusToModel(Model model) {
        List<LoaiSan> loaiSanList = loaiSanService.getAllLoaiSan(); // Giả sử có phương thức này
        model.addAttribute("loaiSanList", loaiSanList);
        model.addAttribute("statuses", List.of("HoatDong", "BaoTri", "TamNgung")); // Danh sách trạng thái sân
    }
}
