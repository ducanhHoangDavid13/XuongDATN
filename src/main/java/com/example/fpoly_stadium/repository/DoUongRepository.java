package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.entity.san.DoUong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoUongRepository extends JpaRepository<DoUong, Long> {
}
