package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.CaDa;
import com.example.fpoly_stadium.repository.CaRepository;
import com.example.fpoly_stadium.services.CaDaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaDaServiceImpl implements CaDaService {
    private final CaRepository caDaRepository;

    @Autowired
    public CaDaServiceImpl(CaRepository caDaRepository) {
        this.caDaRepository = caDaRepository;
    }

    @Override
    public CaDa createCaDa(CaDa caDa) {
        return caDaRepository.save(caDa);
    }

    @Override
    public CaDa updateCaDa(CaDa caDa) {
        if (!caDaRepository.existsById(caDa.getId())) {
            throw new RuntimeException("CaDa not found with id: " + caDa.getId());
        }
        return caDaRepository.save(caDa);
    }

    @Override
    public void deleteCaDa(Integer id) {
        if (!caDaRepository.existsById(id)) {
            throw new RuntimeException("CaDa not found with id: " + id);
        }
        caDaRepository.deleteById(id);
    }

    @Override
    public CaDa getCaDaById(Integer id) {
        Optional<CaDa> opt = caDaRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("CaDa not found with id: " + id));
    }

    @Override
    public List<CaDa> getAllCaDa() {
        return caDaRepository.findAll();
    }
}
