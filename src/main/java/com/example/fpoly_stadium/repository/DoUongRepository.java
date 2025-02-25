package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.san.DoThue;
import com.example.fpoly_stadium.entity.san.DoUong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DoUongRepository extends JpaRepository<DoUong, Integer> {
    List<DoUong> findByTenDoUongContainingIgnoreCase(String keyword);
    List<DoUong> findByTrangThai(Integer trangThai);
    List<DoUong> findByTenDoUongContainingIgnoreCaseAndTrangThai(String keyword, Integer trangThai);
}
