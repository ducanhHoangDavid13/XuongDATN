package com.example.fpoly_stadium.services;
import com.example.fpoly_stadium.model.CaDa;
import java.util.List;
public interface CaDaService {
    CaDa createCaDa(CaDa caDa);
    CaDa updateCaDa(CaDa caDa);
    void deleteCaDa(Integer id);
    CaDa getCaDaById(Integer id);
    List<CaDa> getAllCaDa();
}
