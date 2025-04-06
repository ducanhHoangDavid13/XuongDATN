package com.example.fpoly_stadium.controller;

import com.example.fpoly_stadium.model.CaDa;
import com.example.fpoly_stadium.services.DatLichService;
import com.example.fpoly_stadium.services.CaDaService;
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
@RequestMapping("/admin/timeslots")
public class AdminCaDaController {

    private final CaDaService caDaService;
    private final DatLichService datLichService;

    @Autowired
    public AdminCaDaController(CaDaService caDaService, DatLichService datLichService) {
        this.caDaService = caDaService;
        this.datLichService = datLichService;
    }

    // ✅ Hiển thị danh sách Ca Đá
    @GetMapping
    public String hienThiDanhSach(Model model,
                                  @ModelAttribute("thongBaoThanhCong") String successMsg,
                                  @ModelAttribute("thongBaoLoi") String errorMsg) {
        model.addAttribute("danhSachCaDa", caDaService.getAllCaDa());
        model.addAttribute("tieuDeTrang", "Quản Lý Ca Đá");

        if (!successMsg.isBlank()) model.addAttribute("thongBaoThanhCong", successMsg);
        if (!errorMsg.isBlank()) model.addAttribute("thongBaoLoi", errorMsg);

        return "admin/timeslots/list";
    }

    // ✅ Form thêm mới Ca Đá
    @GetMapping("/new")
    public String hienThiFormThemMoi(Model model) {
        model.addAttribute("caDa", new CaDa());
        model.addAttribute("tieuDeTrang", "Thêm Ca Đá Mới");
        model.addAttribute("laChinhSua", false);
        addStatusesToModel(model);
        return "admin/timeslots/form";
    }

    // ✅ Xử lý thêm mới Ca Đá
    @PostMapping
    public String xuLyThemMoi(@Valid @ModelAttribute("caDa") CaDa caDa,
                              BindingResult bindingResult,
                              RedirectAttributes redirect,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tieuDeTrang", "Thêm Ca Đá Mới");
            model.addAttribute("laChinhSua", false);
            addStatusesToModel(model);
            return "admin/timeslots/form";
        }

        try {
            caDaService.createCaDa(caDa);
            redirect.addFlashAttribute("thongBaoThanhCong", "Thêm ca đá '" + caDa.getTenCa() + "' thành công!");
            return "redirect:/admin/timeslots";
        } catch (Exception e) {
            redirect.addFlashAttribute("thongBaoLoi", "Lỗi khi thêm ca đá: " + e.getMessage());
            return "redirect:/admin/timeslots/new";
        }
    }

    // ✅ Form chỉnh sửa
    @GetMapping("/{id}/edit")
    public String hienThiFormChinhSua(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        try {
            CaDa caDa = caDaService.getCaDaById(id);
            model.addAttribute("caDa", caDa);
            model.addAttribute("tieuDeTrang", "Chỉnh Sửa Ca Đá (ID: " + id + ")");
            model.addAttribute("laChinhSua", true);
            addStatusesToModel(model);
            return "admin/timeslots/form";
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("thongBaoLoi", e.getMessage());
            return "redirect:/admin/timeslots";
        }
    }

    // ✅ Xử lý cập nhật
    @PostMapping("/{id}")
    public String xuLyCapNhat(@PathVariable Integer id,
                              @Valid @ModelAttribute("caDa") CaDa caDa,
                              BindingResult bindingResult,
                              RedirectAttributes redirect,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tieuDeTrang", "Chỉnh Sửa Ca Đá (ID: " + id + ")");
            model.addAttribute("laChinhSua", true);
            addStatusesToModel(model);
            return "admin/timeslots/form";
        }

        try {
            caDa.setId(id);
            caDaService.updateCaDa(caDa);
            redirect.addFlashAttribute("thongBaoThanhCong", "Cập nhật ca đá '" + caDa.getTenCa() + "' thành công!");
            return "redirect:/admin/timeslots";
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("thongBaoLoi", e.getMessage());
            return "redirect:/admin/timeslots/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String xuLyXoa(@PathVariable Integer id, RedirectAttributes redirect) {
        String tenCa = "";

        try {
            CaDa caDa = caDaService.getCaDaById(id);
            tenCa = caDa.getTenCa();

            boolean dangDuocDat = datLichService.getAllBookings().stream()
                    .anyMatch(b -> b.getCaDa().getId().equals(id));

            if (dangDuocDat) {
                throw new DataIntegrityViolationException("Ca đá đang được sử dụng trong lịch đặt.");
            }

            caDaService.deleteCaDa(id);
            redirect.addFlashAttribute("thongBaoThanhCong", "Đã xóa ca đá '" + tenCa + "' thành công!");
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("thongBaoLoi", "Không thể xóa ca đá '" + tenCa + "' vì đang được sử dụng.");
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("thongBaoLoi", e.getMessage());
        } catch (Exception e) {
            redirect.addFlashAttribute("thongBaoLoi", "Lỗi không mong muốn khi xóa ca đá.");
        }

        return "redirect:/admin/timeslots";
    }


    // ✅ Thêm danh sách trạng thái vào model
    private void addStatusesToModel(Model model) {
        model.addAttribute("dsTrangThaiCa", List.of("HoatDong", "TamDung"));
    }
}
