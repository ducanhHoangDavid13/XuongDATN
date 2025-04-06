package com.example.fpoly_stadium.services.impl;
import com.example.fpoly_stadium.model.PhieuGiamGiaApDung;
import com.example.fpoly_stadium.repository.PhieuGiamGiaApDungRepository;
import com.example.fpoly_stadium.services.PhieuGiamGiaApDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuGiamGiaApDungServiceImpl implements PhieuGiamGiaApDungService {

    private final PhieuGiamGiaApDungRepository repository;

    @Autowired
    public PhieuGiamGiaApDungServiceImpl(PhieuGiamGiaApDungRepository repository) {
        this.repository = repository;
    }

    @Override
    public PhieuGiamGiaApDung createPhieuGiamGiaApDung(PhieuGiamGiaApDung phieu) {
        return repository.save(phieu);
    }

    @Override
    public PhieuGiamGiaApDung updatePhieuGiamGiaApDung(PhieuGiamGiaApDung phieu) {
        if (!repository.existsById(phieu.getId())) {
            throw new RuntimeException("PhieuGiamGiaApDung không tồn tại với id: " + phieu.getId());
        }
        return repository.save(phieu);
    }

    @Override
    public void deletePhieuGiamGiaApDung(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("PhieuGiamGiaApDung không tồn tại với id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<PhieuGiamGiaApDung> getPhieuGiamGiaApDungById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<PhieuGiamGiaApDung> getAllPhieuGiamGiaApDung() {
        return repository.findAll();
    }
}
