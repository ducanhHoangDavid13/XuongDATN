package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.san.DoThue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoThueRepository extends JpaRepository<DoThue, Integer> {
    List<DoThue> findByTenDoThueContainingIgnoreCase(String keyword);
    List<DoThue> findByTrangThai(Integer trangThai);
    List<DoThue> findByTenDoThueContainingIgnoreCaseAndTrangThai(String keyword, Integer trangThai);
}
