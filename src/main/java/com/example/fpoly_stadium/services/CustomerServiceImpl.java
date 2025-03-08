package com.example.fpoly_stadium.services;
import com.example.fpoly_stadium.entity.user.KhachHang;
import com.example.fpoly_stadium.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private KhachHangRepository customerRepository;

    @Override
    public List<KhachHang> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public KhachHang getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public KhachHang saveCustomer(KhachHang khachHang) {
        return customerRepository.save(khachHang);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<KhachHang> searchCustomers(String keyword) {
        return customerRepository.findByHoVaTenContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<KhachHang> filterByGender(Boolean gioiTinh) {
        return customerRepository.findByGioiTinh(gioiTinh);
    }
}