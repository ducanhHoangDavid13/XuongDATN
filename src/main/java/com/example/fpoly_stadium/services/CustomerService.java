package com.example.fpoly_stadium.services;
import com.example.fpoly_stadium.entity.user.KhachHang;
import java.util.List;
public interface CustomerService {
    List<KhachHang> getAllCustomers();
    KhachHang getCustomerById(Integer id);
    KhachHang saveCustomer(KhachHang khachHang);
    void deleteCustomer(Integer id);
    List<KhachHang> searchCustomers(String keyword);
    List<KhachHang> filterByGender(Boolean gioiTinh);
}
