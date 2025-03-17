package com.example.fpoly_stadium.repository;



import com.example.fpoly_stadium.entity.san.SanBong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanBongRepository extends JpaRepository<SanBong, Long> {
    @Query("SELECT s FROM SanBong s WHERE LOWER(s.tenSan) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SanBong> searchByTenSan(@Param("keyword") String keyword);

}

