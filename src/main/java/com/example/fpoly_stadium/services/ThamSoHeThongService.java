package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.ThamSoHeThong;

import java.util.List;
import java.util.Optional;

public interface ThamSoHeThongService {
    ThamSoHeThong createThamSo(ThamSoHeThong ts);
    ThamSoHeThong updateThamSo(ThamSoHeThong ts);
    void deleteThamSo(String maThamSo);
    Optional<ThamSoHeThong> getThamSoByMa(String maThamSo);
    List<ThamSoHeThong> getAllThamSo();
}
