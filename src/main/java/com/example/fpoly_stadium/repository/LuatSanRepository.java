package com.example.fpoly_stadium.repository;

import com.example.fpoly_stadium.model.LuatSan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LuatSanRepository extends JpaRepository<LuatSan, Integer> {

}
