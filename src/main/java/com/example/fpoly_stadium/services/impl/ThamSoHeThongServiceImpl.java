package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.ThamSoHeThong;
import com.example.fpoly_stadium.repository.ThamSoHeThongRepository;
import com.example.fpoly_stadium.services.ThamSoHeThongService;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThamSoHeThongServiceImpl implements ThamSoHeThongService {
    private final ThamSoHeThongRepository repository;

    @Autowired
    public ThamSoHeThongServiceImpl(ThamSoHeThongRepository repository) {
        this.repository = repository;
    }

    @Override
    public ThamSoHeThong createThamSo(ThamSoHeThong ts) {
        return repository.save(ts);
    }

    @Override
    public ThamSoHeThong updateThamSo(ThamSoHeThong ts) {
        if (!repository.existsById(Integer.valueOf(ts.getMaThamSo()))) {
            throw new RuntimeException("ThamSoHeThong không tồn tại với ma: " + ts.getMaThamSo());
        }
        return repository.save(ts);
    }

    @Override
    public void deleteThamSo(String maThamSo) {
        if (!repository.existsById(Integer.valueOf(maThamSo))) {
            throw new RuntimeException("ThamSoHeThong không tồn tại với ma: " + maThamSo);
        }
        repository.deleteById(Integer.valueOf(maThamSo));
    }

    @Override
    public Optional<ThamSoHeThong> getThamSoByMa(String maThamSo) {
        return repository.findById(Integer.valueOf(maThamSo));
    }

    @Override
    public List<ThamSoHeThong> getAllThamSo() {
        return repository.findAll();
    }
}
