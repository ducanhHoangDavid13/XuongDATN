package com.example.fpoly_stadium.services.impl;
import com.example.fpoly_stadium.model.PhuPhiHoaDon;
import com.example.fpoly_stadium.repository.PhuPhiHoaDonRepository;
import com.example.fpoly_stadium.services.PhuPhiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PhuPhiHoaDonServiceImpl implements PhuPhiHoaDonService {
    private final PhuPhiHoaDonRepository repository;


    @Autowired
    public PhuPhiHoaDonServiceImpl(PhuPhiHoaDonRepository repository) {
        this.repository = repository;
    }

    @Override
    public PhuPhiHoaDon createPhuPhiHoaDon(PhuPhiHoaDon phuPhi) {
        return repository.save(phuPhi);
    }

    @Override
    public PhuPhiHoaDon updatePhuPhiHoaDon(PhuPhiHoaDon phuPhi) {
        if (!repository.existsById(phuPhi.getId())) {
            throw new RuntimeException("PhuPhiHoaDon không tồn tại với id: " + phuPhi.getId());
        }
        return repository.save(phuPhi);
    }

    @Override
    public void deletePhuPhiHoaDon(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("PhuPhiHoaDon không tồn tại với id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<PhuPhiHoaDon> getPhuPhiHoaDonById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<PhuPhiHoaDon> getAllPhuPhiHoaDon() {
        return repository.findAll();
    }
}
