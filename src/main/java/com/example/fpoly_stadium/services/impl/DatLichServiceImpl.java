package com.example.fpoly_stadium.services.impl;

import com.example.fpoly_stadium.model.LichDat;
import com.example.fpoly_stadium.repository.LichDatRepository;
import com.example.fpoly_stadium.services.DatLichService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DatLichServiceImpl implements DatLichService {
    private final LichDatRepository lichDatRepository;

    @Autowired
    public DatLichServiceImpl(LichDatRepository lichDatRepository) {
        this.lichDatRepository = lichDatRepository;
    }

    @Override
    public LichDat createBooking(LichDat booking) {
        if (!isSlotAvailable(booking.getSanBong().getId(), booking.getCaDa().getId(), booking.getNgayDa())) {
            throw new IllegalStateException("Slot đã được đặt!");
        }
        booking.setTrangThai("CHO_XAC_NHAN");
        return lichDatRepository.save(booking);
    }

    @Override
    public LichDat updateBooking(LichDat booking) {
        LichDat existing = getBookingByIdOrThrow(booking.getId());
        existing.setNgayDa(booking.getNgayDa());
        existing.setCaDa(booking.getCaDa());
        existing.setSanBong(booking.getSanBong());
        existing.setTrangThai(booking.getTrangThai());
        existing.setGhiChu(booking.getGhiChu());
        return lichDatRepository.save(existing);
    }

    @Override
    public Optional<LichDat> getBookingById(Integer id) {
        return lichDatRepository.findById(id);
    }

    @Override
    public LichDat getBookingByIdOrThrow(Integer id) {
        return lichDatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lịch đặt với ID: " + id));
    }

    @Override
    public List<LichDat> getAllBookings() {
        return lichDatRepository.findAll();
    }

    @Override
    public List<LichDat> getBookingsByDate(LocalDate date) {
        return lichDatRepository.findByNgayDa(date);
    }

    @Override
    public List<LichDat> getBookingsByCustomerId(Integer customerId) {
        return lichDatRepository.findByKhachHangId(customerId);
    }

    @Override
    public List<LichDat> getBookingsByStatus(String status) {
        return lichDatRepository.findByTrangThai(status);
    }

    @Override
    public void cancelBooking(Integer bookingId) {
        LichDat booking = getBookingByIdOrThrow(bookingId);
        if (!canUserCancel(booking)) {
            throw new IllegalStateException("Không thể hủy lịch đặt này.");
        }
        booking.setTrangThai("DA_HUY");
        booking.setGhiChu("Hủy bởi người dùng");
        lichDatRepository.save(booking);
    }

    @Override
    public void cancelBookingByAdmin(Integer bookingId, String lyDoHuy) {
        LichDat booking = getBookingByIdOrThrow(bookingId);
        if (!canAdminCancel(booking)) {
            throw new IllegalStateException("Không thể hủy do lịch đã bị hủy trước đó.");
        }
        booking.setTrangThai("DA_HUY");
        booking.setGhiChu("Admin hủy: " + lyDoHuy);
        lichDatRepository.save(booking);
    }

    @Override
    public boolean canAdminCancel(LichDat lichDat) {
        return !"DA_HUY".equalsIgnoreCase(lichDat.getTrangThai());
    }

    @Override
    public boolean canUserCancel(LichDat lichDat) {
        return !"DA_HUY".equalsIgnoreCase(lichDat.getTrangThai())
                && lichDat.getNgayDa().isAfter(LocalDate.now());
    }

    @Override
    public boolean isSlotAvailable(Integer sanBongId, Integer caDaId, LocalDate ngayDa) {
        return lichDatRepository.findBySanBongIdAndCaDaIdAndNgayDa(sanBongId, caDaId, ngayDa).isEmpty();
    }

    @Override
    public List<LichDat> findBookingsFiltered(LocalDate ngayDa, String trangThai, Integer sanBongId, Integer khachHangId) {
        return lichDatRepository.findBookingsFiltered(ngayDa, trangThai, sanBongId, khachHangId);
    }
}
