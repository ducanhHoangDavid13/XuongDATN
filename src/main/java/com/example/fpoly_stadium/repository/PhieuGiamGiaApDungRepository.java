package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.model.PhieuGiamGiaApDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuGiamGiaApDungRepository extends JpaRepository<PhieuGiamGiaApDung, Integer> {
}
