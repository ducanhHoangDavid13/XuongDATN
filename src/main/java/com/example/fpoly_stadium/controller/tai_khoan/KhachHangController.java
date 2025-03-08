package com.example.fpoly_stadium.controller.tai_khoan;


import com.example.fpoly_stadium.entity.user.KhachHang;
import com.example.fpoly_stadium.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listCustomers(@RequestParam(name = "search", required = false) String search,
                                @RequestParam(name = "gender", required = false) Boolean gender,
                                Model model) {
        List<KhachHang> customers;

        if (search != null && !search.isEmpty()) {
            customers = customerService.searchCustomers(search);
        } else if (gender != null) {
            customers = customerService.filterByGender(gender);
        } else {
            customers = customerService.getAllCustomers();
        }

        model.addAttribute("customers", customers);
        return "tai-khoan/khach-hang/khach-hang";
    }
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new KhachHang());
        return "tai-khoan/khach-hang/form";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") KhachHang khachHang) {
        customerService.saveCustomer(khachHang);
        return "redirect:/khach-hang";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        KhachHang khachHang = customerService.getCustomerById(id);
        model.addAttribute("customer", khachHang);
        return "tai-khoan/khach-hang/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return "redirect:/khach-hang";
    }

}