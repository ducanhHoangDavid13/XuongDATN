package com.example.fpoly_stadium.controller;
import com.example.fpoly_stadium.model.PhieuGiamGia;
import com.example.fpoly_stadium.services.PhieuGiamGiaService;
// Import PhieuGiamGiaApDungService nếu cần kiểm tra ràng buộc xóa
// import com.example.fpoly_stadium.services.PhieuGiamGiaApDungService;
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
public class AdminPhieuGiamGiaController {

    private final PhieuGiamGiaService phieuGiamGiaService;
    // private final PhieuGiamGiaApDungService pggApDungService;

    @Autowired
    public AdminPhieuGiamGiaController(PhieuGiamGiaService phieuGiamGiaService /*, PhieuGiamGiaApDungService pggApDungService*/) {
        this.phieuGiamGiaService = phieuGiamGiaService;
        // this.pggApDungService = pggApDungService;
    }

    // Hiển thị danh sách
    @GetMapping
    public String hienThiDanhSach(Model model,
                                  @ModelAttribute("thongBaoThanhCong") String thongBaoThanhCong,
                                  @ModelAttribute("thongBaoLoi") String thongBaoLoi) {
        List<PhieuGiamGia> danhSach = phieuGiamGiaService.getAllPhieuGiamGia();
        model.addAttribute("danhSachPhieuGiamGia", danhSach);
        model.addAttribute("tieuDeTrang", "Quản Lý Phiếu Giảm Giá");
        if (thongBaoThanhCong != null && !thongBaoThanhCong.isEmpty()) model.addAttribute("thongBaoThanhCong", thongBaoThanhCong);
        if (thongBaoLoi != null && !thongBaoLoi.isEmpty()) model.addAttribute("thongBaoLoi", thongBaoLoi);
        return "admin/discounts/list"; // View list.html
    }

    // Hiển thị form thêm mới
    @GetMapping("/new")
    public String hienThiFormThemMoi(Model model, @ModelAttribute("thongBaoLoi") String thongBaoLoi) {
        model.addAttribute("phieuGiamGia", new PhieuGiamGia());
        model.addAttribute("tieuDeTrang", "Thêm Phiếu Giảm Giá Mới");
        model.addAttribute("laChinhSua", false);
        addTypesAndStatusesToModel(model); // Thêm các loại và trạng thái vào model
        if (thongBaoLoi != null && !thongBaoLoi.isEmpty()) model.addAttribute("thongBaoLoi", thongBaoLoi);
        return "admin/discounts/form"; // View form.html
    }

    // Xử lý thêm mới
    @PostMapping
    public String xuLyThemMoi(@Valid @ModelAttribute("phieuGiamGia") PhieuGiamGia phieuGiamGia,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // TODO: Kiểm tra trùng Mã Giảm Giá trong Service hoặc Repository
        // if (phieuGiamGiaService.existsByMaGiamGia(phieuGiamGia.getMaGiamGia())) { ... }

        // TODO: Thêm validation logic (vd: giá trị tối đa chỉ dùng với loại %, ngày kết thúc >= ngày bắt đầu)
        validatePhieuGiamGiaLogic(phieuGiamGia, bindingResult);


        if (bindingResult.hasErrors()) {
            model.addAttribute("tieuDeTrang", "Thêm Phiếu Giảm Giá Mới");
            model.addAttribute("laChinhSua", false);
            addTypesAndStatusesToModel(model);
            return "admin/discounts/form";
        }
        try {
            // Đặt số lượng đã dùng là 0 khi tạo mới
            phieuGiamGia.setSoLuongDaSuDung(0);
            phieuGiamGiaService.createPhieuGiamGia(phieuGiamGia);
            redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Thêm phiếu giảm giá '" + phieuGiamGia.getMaGiamGia() + "' thành công!");
            return "redirect:/admin/discounts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi khi thêm phiếu giảm giá: " + e.getMessage());
            redirectAttributes.addFlashAttribute("phieuGiamGia", phieuGiamGia); // Giữ lại dữ liệu form
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.phieuGiamGia", bindingResult);
            return "redirect:/admin/discounts/new";
        }
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/{id}/edit")
    public String hienThiFormChinhSua(@PathVariable("id") Integer id, Model model,
                                      RedirectAttributes redirectAttributes,
                                      @ModelAttribute("thongBaoLoi") String thongBaoLoi) {
        try {
            PhieuGiamGia phieuGiamGia = phieuGiamGiaService.getPhieuGiamGiaById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Phiếu giảm giá ID: " + id));
            model.addAttribute("phieuGiamGia", phieuGiamGia);
            model.addAttribute("tieuDeTrang", "Chỉnh Sửa Phiếu Giảm Giá (ID: " + id + ")");
            model.addAttribute("laChinhSua", true);
            addTypesAndStatusesToModel(model);
            if (thongBaoLoi != null && !thongBaoLoi.isEmpty()) model.addAttribute("thongBaoLoi", thongBaoLoi);
            return "admin/discounts/form";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
            return "redirect:/admin/discounts";
        }
    }

    // Xử lý cập nhật
    @PostMapping("/{id}")
    public String xuLyCapNhat(@PathVariable("id") Integer id,
                              @Valid @ModelAttribute("phieuGiamGia") PhieuGiamGia phieuGiamGia,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // TODO: Kiểm tra trùng Mã Giảm Giá (loại trừ chính nó)
        // Optional<PhieuGiamGia> existing = phieuGiamGiaService.findByMaGiamGia(phieuGiamGia.getMaGiamGia());
        // if(existing.isPresent() && !existing.get().getId().equals(id)) { ... }

        validatePhieuGiamGiaLogic(phieuGiamGia, bindingResult); // Validate logic

        if (bindingResult.hasErrors()) {
            model.addAttribute("tieuDeTrang", "Chỉnh Sửa Phiếu Giảm Giá (ID: " + id + ")");
            model.addAttribute("laChinhSua", true);
            addTypesAndStatusesToModel(model);
            phieuGiamGia.setId(id); // Giữ ID
            model.addAttribute("phieuGiamGia", phieuGiamGia);
            return "admin/discounts/form";
        }
        phieuGiamGia.setId(id);
        try {
            // Giữ nguyên số lượng đã sử dụng khi cập nhật thông tin khác
            PhieuGiamGia currentPhieu = phieuGiamGiaService.getPhieuGiamGiaById(id).orElse(null);
            if(currentPhieu != null) {
                phieuGiamGia.setSoLuongDaSuDung(currentPhieu.getSoLuongDaSuDung());
            } else {
                throw new EntityNotFoundException("Không tìm thấy phiếu để cập nhật.");
            }

            phieuGiamGiaService.updatePhieuGiamGia(phieuGiamGia);
            redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Cập nhật phiếu giảm giá '" + phieuGiamGia.getMaGiamGia() + "' thành công!");
            return "redirect:/admin/discounts";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
            return "redirect:/admin/discounts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi khi cập nhật phiếu giảm giá: " + e.getMessage());
            redirectAttributes.addFlashAttribute("phieuGiamGia", phieuGiamGia);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.phieuGiamGia", bindingResult);
            return "redirect:/admin/discounts/" + id + "/edit";
        }
    }

    // Xử lý xóa
    @PostMapping("/{id}/delete")
    public String xuLyXoa(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            // *** KIỂM TRA RÀNG BUỘC VỚI PhieuGiamGiaApDungService ***
            // boolean dangSuDung = pggApDungService.existsByPhieuGiamGiaId(id);
            // if (dangSuDung) { throw new DataIntegrityViolationException("..."); }

            String maPhieu = "";
            Optional<PhieuGiamGia> optPhieu = phieuGiamGiaService.getPhieuGiamGiaById(id);
            if(optPhieu.isPresent()){
                maPhieu = optPhieu.get().getMaGiamGia();
            } else {
                throw new EntityNotFoundException("Không tìm thấy Phiếu giảm giá ID: " + id);
            }

            phieuGiamGiaService.deletePhieuGiamGia(id);
            redirectAttributes.addFlashAttribute("thongBaoThanhCong", "Đã xóa phiếu giảm giá '" + maPhieu + "' thành công!");
        } catch (RuntimeException e) { // Bắt lỗi chung từ service (not found hoặc ràng buộc)
            redirectAttributes.addFlashAttribute("thongBaoLoi", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Lỗi không mong muốn khi xóa phiếu giảm giá.");
        }
        return "redirect:/admin/discounts";
    }

    // Hàm tiện ích
    private void addTypesAndStatusesToModel(Model model) {
        model.addAttribute("loaiGiamGiaList", List.of("PhanTram", "SoTien"));
        model.addAttribute("trangThaiList", List.of("HoatDong", "HetHan", "TamDung", "HetLuot"));
    }

    // Hàm kiểm tra logic nghiệp vụ (ví dụ)
    private void validatePhieuGiamGiaLogic(PhieuGiamGia phieu, BindingResult bindingResult) {
        if ("PhanTram".equals(phieu.getLoaiGiamGia())) {
            if (phieu.getGiaTri() < 0 || phieu.getGiaTri() > 100) {
                bindingResult.addError(new FieldError("phieuGiamGia", "giaTri", "Giá trị phần trăm phải từ 0 đến 100."));
            }
            // Giá trị giảm tối đa có thể là null nếu không giới hạn
        } else if ("SoTien".equals(phieu.getLoaiGiamGia())) {
            if (phieu.getGiaTri() <= 0) {
                bindingResult.addError(new FieldError("phieuGiamGia", "giaTri", "Giá trị tiền giảm phải lớn hơn 0."));
            }
            // Đảm bảo giá trị giảm tối đa là null khi loại là Số tiền
            if (phieu.getGiaTriGiamToiDa() != null) {
                // Có thể tự động set null hoặc báo lỗi tùy logic
                // phieu.setGiaTriGiamToiDa(null);
                bindingResult.addError(new FieldError("phieuGiamGia", "giaTriGiamToiDa", "Không áp dụng giá trị giảm tối đa cho loại giảm giá theo số tiền."));
            }
        }

        if (phieu.getNgayBatDau() != null && phieu.getNgayKetThuc() != null && phieu.getNgayKetThuc().isBefore(phieu.getNgayBatDau())) {
            bindingResult.addError(new FieldError("phieuGiamGia", "ngayKetThuc", "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu."));
        }

        if(phieu.getSoLuongPhatHanh() != null && phieu.getSoLuongPhatHanh() <= 0) {
            bindingResult.addError(new FieldError("phieuGiamGia", "soLuongPhatHanh", "Số lượng phát hành phải lớn hơn 0 (hoặc để trống nếu không giới hạn)."));
        }
    }
}