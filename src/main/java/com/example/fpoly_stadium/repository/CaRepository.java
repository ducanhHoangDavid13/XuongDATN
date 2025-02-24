package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.san.Ca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaRepository extends JpaRepository<Ca, Long> {
}
