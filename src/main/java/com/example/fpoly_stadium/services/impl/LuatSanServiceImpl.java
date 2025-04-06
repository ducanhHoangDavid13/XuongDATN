package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.LuatSan;
import com.example.fpoly_stadium.repository.LuatSanRepository;
import com.example.fpoly_stadium.services.LuatSanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LuatSanServiceImpl implements LuatSanService {

    private final LuatSanRepository luatSanRepository;

    @Autowired
    public LuatSanServiceImpl(LuatSanRepository luatSanRepository) {
        this.luatSanRepository = luatSanRepository;
    }

    @Override
    public LuatSan createLuatSan(LuatSan luatSan) {
        return luatSanRepository.save(luatSan);
    }

    @Override
    public LuatSan updateLuatSan(LuatSan luatSan) {
        if (!luatSanRepository.existsById(luatSan.getId())) {
            throw new RuntimeException("LuatSan not found with id: " + luatSan.getId());
        }
        return luatSanRepository.save(luatSan);
    }

    @Override
    public void deleteLuatSan(Integer id) {
        if (!luatSanRepository.existsById(id)) {
            throw new RuntimeException("LuatSan not found with id: " + id);
        }
        luatSanRepository.deleteById(id);
    }

    @Override
    public LuatSan getLuatSanById(Integer id) {
        Optional<LuatSan> opt = luatSanRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("LuatSan not found with id: " + id));
    }

    @Override
    public List<LuatSan> getAllLuatSan() {
        return luatSanRepository.findAll();
    }
}
