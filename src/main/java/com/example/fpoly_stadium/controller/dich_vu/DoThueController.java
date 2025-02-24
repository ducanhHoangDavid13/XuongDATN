package com.example.fpoly_stadium.controller.dich_vu;
import com.example.fpoly_stadium.entity.san.DoThue;
import com.example.fpoly_stadium.services.DoThueServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/do-thue")
public class DoThueController{
    @Autowired
    private DoThueServices doThueService;
    @GetMapping()
    public String getAllDoThue(Model model) {
        model.addAttribute("doThueList", doThueService.getAllDoThue());

        return "/dich-vu/do-thue-chi-tiet";
    }

    @GetMapping("/detail/{id}")
    public String getDoThueDetail(@PathVariable Integer id, Model model) {
        Optional<DoThue> doThue = doThueService.getDoThueById(id);
        doThue.ifPresent(value -> model.addAttribute("doThue", value));
        return doThue.isPresent() ? "do-thue-detail" : "redirect:/do-thue";
    }

    @PostMapping("/update/{id}")
    public String updateDoThue(@PathVariable Integer id, @ModelAttribute DoThue doThue) {
        doThueService.updateDoThue(id, doThue);
        return "redirect:/do-thue";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoThue(@PathVariable Integer id) {
        doThueService.deleteDoThue(id);
        return "redirect:/dich-vu/do-thue-chi-tiet";
    }
}
