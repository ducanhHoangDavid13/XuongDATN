package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.model.PhuPhiHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PhuPhiHoaDonRepository extends JpaRepository<PhuPhiHoaDon, Integer> {

}
