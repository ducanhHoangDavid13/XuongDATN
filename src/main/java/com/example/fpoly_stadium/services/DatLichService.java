package com.example.fpoly_stadium.services;

import com.example.fpoly_stadium.model.LichDat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DatLichService {
    LichDat createBooking(LichDat booking); // alias cho createLichDat
    LichDat updateBooking(LichDat booking);

    Optional<LichDat> getBookingById(Integer id);
    LichDat getBookingByIdOrThrow(Integer id); // dễ dùng hơn ở controller

    List<LichDat> getAllBookings();
    List<LichDat> getBookingsByDate(LocalDate date);
    List<LichDat> getBookingsByCustomerId(Integer customerId);
    List<LichDat> getBookingsByStatus(String status);

    void cancelBooking(Integer bookingId); // Người dùng tự hủy
    void cancelBookingByAdmin(Integer bookingId, String lyDoHuy); // Admin hủy + ghi chú

    boolean canAdminCancel(LichDat lichDat);
    boolean canUserCancel(LichDat lichDat);
    boolean isSlotAvailable(Integer sanBongId, Integer caDaId, LocalDate ngayDa); // kiểm tra trùng slot

    /**
     * Tìm kiếm nâng cao theo nhiều tiêu chí.
     */
    List<LichDat> findBookingsFiltered(LocalDate ngayDa, String trangThai, Integer sanBongId, Integer khachHangId);

}
