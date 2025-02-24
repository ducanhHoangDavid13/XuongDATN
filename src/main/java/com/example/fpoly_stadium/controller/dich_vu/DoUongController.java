package com.example.fpoly_stadium.controller.dich_vu;
import com.example.fpoly_stadium.entity.san.DoUong;
import com.example.fpoly_stadium.services.DoUongServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/do-uong")
public class DoUongController {
    @Autowired
    private DoUongServices doUongService;

    @GetMapping("/hien-thi")
    public String getAllDoUong(Model model) {
        model.addAttribute("doUongList", doUongService.getAllDoUong());
        return "/dich-vu/do-uong-chi-tiet";
    }

    @GetMapping("/detail/{id}")
    public String getDoUongDetail(@PathVariable Integer id, Model model) {
        Optional<DoUong> doUong = doUongService.getDoUongById(id);
        if (doUong.isPresent()) {
            model.addAttribute("doUong", doUong.get());
            return "dich-vu/do_uong_detail";
        } else {
            return "redirect:/do-uong/hien-thi";
        }
    }

    @PostMapping("/update/{id}")
    public String updateDoUong(@PathVariable Integer id, @ModelAttribute DoUong doUong) {
        doUongService.updateDoUong(id, doUong);
        return "redirect:/dich-vu";
    }

    @PostMapping("/add")
    public String addDoUong(@ModelAttribute DoUong doUong) {
        doUongService.saveDoUong(doUong);
        return "redirect:/do-uong/hien-thi";
    }
    @GetMapping("/delete/{id}")
    public String deleteDoUong(@PathVariable Integer id) {
        doUongService.deleteDoUong(id);
        return "redirect:/do-uong/hien-thi";
    }
}
