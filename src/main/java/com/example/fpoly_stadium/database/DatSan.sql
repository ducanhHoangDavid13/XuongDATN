Create database QuanLyDatSan
go
use QuanLyDatSan
go
IF OBJECT_ID('dbo.TaiKhoan', 'U') IS NOT NULL
    DROP TABLE dbo.TaiKhoan;
GO

CREATE TABLE dbo.TaiKhoan (
    id INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) NOT NULL UNIQUE,         -- Tên đăng nhập (Thường không cần Unicode)
    password_hash VARCHAR(255) NOT NULL,          -- Lưu mật khẩu đã HASH
    email VARCHAR(100) UNIQUE,                    -- Email (Thường không cần Unicode)
    ho_ten NVARCHAR(100),                       -- Tên có thể cần Unicode
    so_dien_thoai VARCHAR(20) UNIQUE,             -- Số điện thoại (Chỉ chứa số và ký tự cơ bản)
    dia_chi NVARCHAR(255),                      -- Địa chỉ có thể cần Unicode
    anh_dai_dien VARCHAR(255),                  -- Đường dẫn ảnh (VARCHAR là đủ)
    loai_tai_khoan NVARCHAR(20) NOT NULL CHECK (loai_tai_khoan IN (N'KhachHang', N'NhanVien', N'ChuSan')),
    trang_thai NVARCHAR(20) NOT NULL CHECK (trang_thai IN (N'HoatDong', N'BiKhoa')) DEFAULT N'HoatDong',
    ngay_tao DATETIME2(3) DEFAULT GETDATE(),     -- Độ chính xác mili giây là đủ
    ngay_cap_nhat DATETIME2(3) DEFAULT GETDATE()
);
GO
-- Index cho tìm kiếm/login nhanh
CREATE NONCLUSTERED INDEX IX_TaiKhoan_Username ON dbo.TaiKhoan(username);
CREATE NONCLUSTERED INDEX IX_TaiKhoan_Email ON dbo.TaiKhoan(email);
CREATE NONCLUSTERED INDEX IX_TaiKhoan_SoDienThoai ON dbo.TaiKhoan(so_dien_thoai);
CREATE NONCLUSTERED INDEX IX_TaiKhoan_LoaiTaiKhoan ON dbo.TaiKhoan(loai_tai_khoan);
GO
-- Trigger cập nhật ngày
CREATE TRIGGER TRG_TaiKhoan_UpdateDate_Opt
ON dbo.TaiKhoan
AFTER UPDATE
AS BEGIN
    IF UPDATE(ngay_cap_nhat) RETURN;
    UPDATE t SET ngay_cap_nhat = GETDATE() FROM dbo.TaiKhoan t JOIN inserted i ON t.id = i.id;
END;
GO
-- Dữ liệu mẫu (Nhớ hash password)
INSERT INTO dbo.TaiKhoan (username, password_hash, email, ho_ten, so_dien_thoai, loai_tai_khoan) VALUES
('admin', '123456', 'admin@sanbong.com', N'Chủ Sân', '0900000000', N'ChuSan'),
('nhanvien1', '123', 'nv1@sanbong.com', N'Nguyễn Văn A', '0911111111', N'NhanVien'),
('khachhang1', '123', 'kh1@email.com', N'Trần Thị B', '0922222222', N'KhachHang');
GO

-- ==================================================
-- Bảng: Loại Sân (Pitch Type)
-- ==================================================
IF OBJECT_ID('dbo.LoaiSan', 'U') IS NOT NULL DROP TABLE dbo.LoaiSan; GO
CREATE TABLE dbo.LoaiSan (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_loai_san NVARCHAR(50) NOT NULL UNIQUE,
    mo_ta NVARCHAR(500) -- Giới hạn độ dài nếu có thể
);
GO
INSERT INTO dbo.LoaiSan (ten_loai_san) VALUES (N'Sân 5'), (N'Sân 7'), (N'Sân 11'); GO

-- ==================================================
-- Bảng: Sân Bóng (Pitch)
-- ==================================================
IF OBJECT_ID('dbo.SanBong', 'U') IS NOT NULL DROP TABLE dbo.SanBong; GO
CREATE TABLE dbo.SanBong (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_san NVARCHAR(100) NOT NULL,
    loai_san_id INT NOT NULL,
    dia_chi NVARCHAR(255),
    suc_chua SMALLINT, -- Số người thường không quá lớn
    anh_dai_dien VARCHAR(255),
    tien_ich NVARCHAR(1000), -- Giới hạn độ dài nếu có thể
    mo_ta NVARCHAR(1000), -- Giới hạn độ dài nếu có thể
    trang_thai NVARCHAR(20) NOT NULL CHECK (trang_thai IN (N'HoatDong', N'BaoTri', N'TamNgung')) DEFAULT N'HoatDong',
    CONSTRAINT FK_SanBong_LoaiSan_Opt FOREIGN KEY (loai_san_id) REFERENCES dbo.LoaiSan(id)
);
GO
-- Index cho khóa ngoại và trạng thái
CREATE NONCLUSTERED INDEX IX_SanBong_LoaiSanId ON dbo.SanBong(loai_san_id);
CREATE NONCLUSTERED INDEX IX_SanBong_TrangThai ON dbo.SanBong(trang_thai);
GO

-- ==================================================
-- Bảng: Ca Đá (Time Slot)
-- ==================================================
IF OBJECT_ID('dbo.CaDa', 'U') IS NOT NULL DROP TABLE dbo.CaDa; GO
CREATE TABLE dbo.CaDa (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_ca NVARCHAR(50),
    thoi_gian_bat_dau TIME(0) NOT NULL, -- Độ chính xác giây là đủ
    thoi_gian_ket_thuc TIME(0) NOT NULL,
    gia_ca DECIMAL(12, 2) NOT NULL CHECK (gia_ca >= 0),
    trang_thai NVARCHAR(20) NOT NULL CHECK (trang_thai IN (N'HoatDong', N'TamDung')) DEFAULT N'HoatDong'
);
GO
-- Index cho trạng thái
CREATE NONCLUSTERED INDEX IX_CaDa_TrangThai ON dbo.CaDa(trang_thai);
GO
INSERT INTO dbo.CaDa (ten_ca, thoi_gian_bat_dau, thoi_gian_ket_thuc, gia_ca) VALUES
(N'Ca 1', '06:00:00', '08:30:00', 250000), (N'Ca 2', '09:00:00', '11:30:00', 300000),
(N'Ca 3', '12:00:00', '14:30:00', 300000), (N'Ca 4', '15:00:00', '17:30:00', 350000),
(N'Ca 5', '18:00:00', '20:30:00', 400000), (N'Ca 6', '21:00:00', '23:30:00', 350000);
GO

-- ==================================================
-- Bảng: Lịch Đặt (Booking)
-- ==================================================
IF OBJECT_ID('dbo.LichDat', 'U') IS NOT NULL DROP TABLE dbo.LichDat; GO
CREATE TABLE dbo.LichDat (
    id INT PRIMARY KEY IDENTITY(1,1),
    khach_hang_id INT NOT NULL,
    san_bong_id INT NOT NULL,
    ca_da_id INT NOT NULL,
    nhan_vien_checkin_id INT NULL,
    ngay_da DATE NOT NULL,
    thoi_gian_dat DATETIME2(3) DEFAULT GETDATE(),
    thoi_gian_checkin DATETIME2(3) NULL,
    ghi_chu NVARCHAR(500), -- Giới hạn độ dài
    so_lan_doi_lich TINYINT DEFAULT 0 CHECK (so_lan_doi_lich >= 0), -- TINYINT là đủ (0-255 lần)
    trang_thai NVARCHAR(30) NOT NULL CHECK (trang_thai IN (
        N'ChoXacNhan', N'DaXacNhan', N'ChoCheckIn', N'DaCheckIn', N'DangDa', N'HoanThanh', N'DaHuy', N'KhongDen'
    )) DEFAULT N'DaXacNhan',
    CONSTRAINT FK_LichDat_KhachHang_Opt FOREIGN KEY (khach_hang_id) REFERENCES dbo.TaiKhoan(id),
    CONSTRAINT FK_LichDat_SanBong_Opt FOREIGN KEY (san_bong_id) REFERENCES dbo.SanBong(id),
    CONSTRAINT FK_LichDat_CaDa_Opt FOREIGN KEY (ca_da_id) REFERENCES dbo.CaDa(id),
    -- Quan trọng: xem xét ON DELETE SET NULL nếu tài khoản nhân viên bị xóa
    CONSTRAINT FK_LichDat_NhanVienCheckin_Opt FOREIGN KEY (nhan_vien_checkin_id) REFERENCES dbo.TaiKhoan(id) ON DELETE SET NULL
);
GO
-- Chỉ mục tối ưu cho việc tìm lịch đặt trống và quản lý
CREATE NONCLUSTERED INDEX IX_LichDat_NgayDa_San_Ca ON dbo.LichDat(ngay_da, san_bong_id, ca_da_id);
CREATE NONCLUSTERED INDEX IX_LichDat_KhachHangId ON dbo.LichDat(khach_hang_id);
CREATE NONCLUSTERED INDEX IX_LichDat_TrangThai ON dbo.LichDat(trang_thai);
CREATE NONCLUSTERED INDEX IX_LichDat_NhanVienCheckinId ON dbo.LichDat(nhan_vien_checkin_id) WHERE nhan_vien_checkin_id IS NOT NULL; -- Index có điều kiện
GO

-- ==================================================
-- Bảng: Hình Thức Thanh Toán (Payment Method)
-- ==================================================
IF OBJECT_ID('dbo.HinhThucThanhToan', 'U') IS NOT NULL DROP TABLE dbo.HinhThucThanhToan; GO
CREATE TABLE dbo.HinhThucThanhToan (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_hinh_thuc NVARCHAR(50) NOT NULL UNIQUE
);
GO
INSERT INTO dbo.HinhThucThanhToan (ten_hinh_thuc) VALUES (N'Tiền mặt'), (N'Chuyển khoản'), (N'VNPAY'); GO

-- ==================================================
-- Bảng: Hóa Đơn (Invoice)
-- ==================================================
IF OBJECT_ID('dbo.HoaDon', 'U') IS NOT NULL DROP TABLE dbo.HoaDon; GO
CREATE TABLE dbo.HoaDon (
    id INT PRIMARY KEY IDENTITY(1,1),
    lich_dat_id INT NOT NULL UNIQUE,
    nhan_vien_lap_id INT NULL, -- Có thể NULL nếu hệ thống tự tạo hoặc nhân viên bị xóa
    ngay_lap DATETIME2(3) DEFAULT GETDATE(),
    tong_tien_san DECIMAL(12, 2) NOT NULL CHECK (tong_tien_san >= 0) DEFAULT 0,
    tong_tien_dich_vu DECIMAL(12, 2) NOT NULL CHECK (tong_tien_dich_vu >= 0) DEFAULT 0,
    tong_tien_phu_phi DECIMAL(12, 2) NOT NULL CHECK (tong_tien_phu_phi >= 0) DEFAULT 0,
    tong_tien_giam_gia DECIMAL(12, 2) NOT NULL CHECK (tong_tien_giam_gia >= 0) DEFAULT 0,
    tong_thanh_toan DECIMAL(12, 2) NOT NULL CHECK (tong_thanh_toan >= 0) DEFAULT 0, -- Nên được tính toán bởi ứng dụng/trigger
    hinh_thuc_thanh_toan_id INT NULL, -- Có thể NULL nếu chưa thanh toán
    ma_giao_dich_online VARCHAR(255) NULL,
    trang_thai NVARCHAR(20) NOT NULL CHECK (trang_thai IN (N'ChuaThanhToan', N'DaThanhToan', N'DaHuy')) DEFAULT N'ChuaThanhToan',
    ghi_chu NVARCHAR(500),
    CONSTRAINT FK_HoaDon_LichDat_Opt FOREIGN KEY (lich_dat_id) REFERENCES dbo.LichDat(id) ON DELETE CASCADE, -- Xóa lịch đặt thì xóa hóa đơn
    CONSTRAINT FK_HoaDon_NhanVienLap_Opt FOREIGN KEY (nhan_vien_lap_id) REFERENCES dbo.TaiKhoan(id) ON DELETE SET NULL, -- Set NULL nếu nhân viên bị xóa
    CONSTRAINT FK_HoaDon_HinhThucThanhToan_Opt FOREIGN KEY (hinh_thuc_thanh_toan_id) REFERENCES dbo.HinhThucThanhToan(id)
);
GO
-- Index cho khóa ngoại và trạng thái
CREATE NONCLUSTERED INDEX IX_HoaDon_LichDatId ON dbo.HoaDon(lich_dat_id);
CREATE NONCLUSTERED INDEX IX_HoaDon_NhanVienLapId ON dbo.HoaDon(nhan_vien_lap_id) WHERE nhan_vien_lap_id IS NOT NULL;
CREATE NONCLUSTERED INDEX IX_HoaDon_TrangThai ON dbo.HoaDon(trang_thai);
CREATE NONCLUSTERED INDEX IX_HoaDon_HinhThucThanhToanId ON dbo.HoaDon(hinh_thuc_thanh_toan_id) WHERE hinh_thuc_thanh_toan_id IS NOT NULL;
GO

-- ==================================================
-- Bảng: Loại Dịch Vụ (Service Type)
-- ==================================================
IF OBJECT_ID('dbo.LoaiDichVu', 'U') IS NOT NULL DROP TABLE dbo.LoaiDichVu; GO
CREATE TABLE dbo.LoaiDichVu (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_loai NVARCHAR(50) NOT NULL UNIQUE
);
GO
INSERT INTO dbo.LoaiDichVu (ten_loai) VALUES (N'Nước uống'), (N'Đồ thuê'); GO

-- ==================================================
-- Bảng: Dịch Vụ (Service)
-- ==================================================
IF OBJECT_ID('dbo.DichVu', 'U') IS NOT NULL DROP TABLE dbo.DichVu; GO
CREATE TABLE dbo.DichVu (
    id INT PRIMARY KEY IDENTITY(1,1),
    loai_dich_vu_id INT NOT NULL,
    ten_dich_vu NVARCHAR(100) NOT NULL,
    don_vi_tinh NVARCHAR(20) DEFAULT N'Cái',
    gia DECIMAL(10, 2) NOT NULL CHECK (gia >= 0),
    so_luong_ton_kho INT CHECK (so_luong_ton_kho >= 0) NULL,
    anh_minh_hoa VARCHAR(255),
    mo_ta NVARCHAR(500),
    trang_thai NVARCHAR(20) NOT NULL CHECK (trang_thai IN (N'DangKinhDoanh', N'NgungKinhDoanh', N'TamHet')) DEFAULT N'DangKinhDoanh',
    CONSTRAINT FK_DichVu_LoaiDichVu_Opt FOREIGN KEY (loai_dich_vu_id) REFERENCES dbo.LoaiDichVu(id)
);
GO
-- Index cho khóa ngoại, tên và trạng thái
CREATE NONCLUSTERED INDEX IX_DichVu_LoaiDichVuId ON dbo.DichVu(loai_dich_vu_id);
CREATE NONCLUSTERED INDEX IX_DichVu_TenDichVu ON dbo.DichVu(ten_dich_vu);
CREATE NONCLUSTERED INDEX IX_DichVu_TrangThai ON dbo.DichVu(trang_thai);
GO
-- Dữ liệu mẫu
INSERT INTO dbo.DichVu (loai_dich_vu_id, ten_dich_vu, don_vi_tinh, gia, so_luong_ton_kho, trang_thai) VALUES
((SELECT id FROM LoaiDichVu WHERE ten_loai = N'Nước uống'), N'Nước Khoáng Lavie', N'Chai', 10000, 98, N'DangKinhDoanh'),
((SELECT id FROM LoaiDichVu WHERE ten_loai = N'Nước uống'), N'Trà Chanh C2', N'Chai', 10000, 47, N'DangKinhDoanh'),
((SELECT id FROM LoaiDichVu WHERE ten_loai = N'Nước uống'), N'Coca Cola', N'Lon', 7000, 198, N'DangKinhDoanh'),
((SELECT id FROM LoaiDichVu WHERE ten_loai = N'Nước uống'), N'Sting Dâu Đỏ', N'Chai', 11000, 147, N'DangKinhDoanh'),
((SELECT id FROM LoaiDichVu WHERE ten_loai = N'Đồ thuê'), N'Áo Đấu CLB Vàng', N'Bộ', 15000, 18, N'DangKinhDoanh'),
((SELECT id FROM LoaiDichVu WHERE ten_loai = N'Đồ thuê'), N'Giày Đá Bóng Vàng', N'Đôi', 9000, 13, N'DangKinhDoanh'),
((SELECT id FROM LoaiDichVu WHERE ten_loai = N'Đồ thuê'), N'Băng Đội Trưởng', N'Cái', 10000, 29, N'DangKinhDoanh');
GO

-- ==================================================
-- Bảng: Chi Tiết Hóa Đơn (Invoice Detail)
-- ==================================================
IF OBJECT_ID('dbo.ChiTietHoaDon', 'U') IS NOT NULL DROP TABLE dbo.ChiTietHoaDon; GO
CREATE TABLE dbo.ChiTietHoaDon (
    id INT PRIMARY KEY IDENTITY(1,1),
    hoa_don_id INT NOT NULL,
    dich_vu_id INT NOT NULL,
    ten_dich_vu_tai_thoi_diem NVARCHAR(100) NOT NULL,
    so_luong INT NOT NULL CHECK (so_luong > 0) DEFAULT 1,
    don_gia_tai_thoi_diem DECIMAL(12, 2) NOT NULL,
    thanh_tien AS (CAST(so_luong AS DECIMAL(12,2)) * don_gia_tai_thoi_diem) PERSISTED, -- Computed column, lưu trữ giá trị tính toán
    CONSTRAINT FK_ChiTietHoaDon_HoaDon_Opt FOREIGN KEY (hoa_don_id) REFERENCES dbo.HoaDon(id) ON DELETE CASCADE,
    CONSTRAINT FK_ChiTietHoaDon_DichVu_Opt FOREIGN KEY (dich_vu_id) REFERENCES dbo.DichVu(id)
);
GO
-- Index cho khóa ngoại
CREATE NONCLUSTERED INDEX IX_ChiTietHoaDon_HoaDonId ON dbo.ChiTietHoaDon(hoa_don_id);
CREATE NONCLUSTERED INDEX IX_ChiTietHoaDon_DichVuId ON dbo.ChiTietHoaDon(dich_vu_id);
GO

-- ==================================================
-- Bảng: Phụ Phí Hóa Đơn (Invoice Surcharge)
-- ==================================================
IF OBJECT_ID('dbo.PhuPhiHoaDon', 'U') IS NOT NULL DROP TABLE dbo.PhuPhiHoaDon; GO
CREATE TABLE dbo.PhuPhiHoaDon (
    id INT PRIMARY KEY IDENTITY(1,1),
    hoa_don_id INT NOT NULL,
    ten_phu_phi NVARCHAR(100) NOT NULL,
    so_tien DECIMAL(12, 2) NOT NULL CHECK (so_tien >= 0),
    ghi_chu NVARCHAR(500),
    CONSTRAINT FK_PhuPhiHoaDon_HoaDon_Opt FOREIGN KEY (hoa_don_id) REFERENCES dbo.HoaDon(id) ON DELETE CASCADE
);
GO
-- Index cho khóa ngoại
CREATE NONCLUSTERED INDEX IX_PhuPhiHoaDon_HoaDonId ON dbo.PhuPhiHoaDon(hoa_don_id);
GO

-- ==================================================
-- Bảng: Phiếu Giảm Giá (Discount Voucher)
-- ==================================================
IF OBJECT_ID('dbo.PhieuGiamGia', 'U') IS NOT NULL DROP TABLE dbo.PhieuGiamGia; GO
CREATE TABLE dbo.PhieuGiamGia (
    id INT PRIMARY KEY IDENTITY(1,1),
    ma_giam_gia VARCHAR(50) NOT NULL UNIQUE,
    mo_ta NVARCHAR(500) NOT NULL,
    loai_giam_gia NVARCHAR(20) NOT NULL CHECK (loai_giam_gia IN (N'PhanTram', N'SoTien')),
    gia_tri FLOAT NOT NULL CHECK (gia_tri > 0),
    gia_tri_giam_toi_da DECIMAL(12, 2) NULL CHECK (gia_tri_giam_toi_da >= 0),
    dieu_kien_hoa_don_toi_thieu DECIMAL(12, 2) NULL CHECK (dieu_kien_hoa_don_toi_thieu >= 0),
    so_luong_phat_hanh INT NULL,
    so_luong_da_su_dung INT DEFAULT 0,
    ngay_bat_dau DATE NULL,
    ngay_ket_thuc DATE NULL,
    trang_thai NVARCHAR(20) NOT NULL CHECK (trang_thai IN (N'HoatDong', N'HetHan', N'TamDung', N'HetLuot')) DEFAULT N'HoatDong',
    ap_dung_cho_loai_khach_id INT NULL -- FK đến bảng Hạng Khách Hàng (nếu có)
);
GO
ALTER TABLE dbo.PhieuGiamGia ADD CONSTRAINT CK_PhieuGiamGia_NgayKetThuc_Opt CHECK (ngay_ket_thuc IS NULL OR ngay_bat_dau IS NULL OR ngay_ket_thuc >= ngay_bat_dau);
GO
ALTER TABLE dbo.PhieuGiamGia ADD CONSTRAINT CK_PhieuGiamGia_SoLuong_Opt CHECK (so_luong_phat_hanh IS NULL OR so_luong_da_su_dung <= so_luong_phat_hanh);
GO
-- Index cho mã giảm giá và trạng thái
CREATE NONCLUSTERED INDEX IX_PhieuGiamGia_MaGiamGia ON dbo.PhieuGiamGia(ma_giam_gia);
CREATE NONCLUSTERED INDEX IX_PhieuGiamGia_TrangThai ON dbo.PhieuGiamGia(trang_thai);
GO

-- ==================================================
-- Bảng: Phiếu Giảm Giá Đã Áp Dụng (Applied Discount)
-- ==================================================
IF OBJECT_ID('dbo.PhieuGiamGiaApDung', 'U') IS NOT NULL DROP TABLE dbo.PhieuGiamGiaApDung; GO
CREATE TABLE dbo.PhieuGiamGiaApDung (
    id INT PRIMARY KEY IDENTITY(1,1),
    phieu_giam_gia_id INT NOT NULL,
    hoa_don_id INT NOT NULL UNIQUE,
    ngay_ap_dung DATETIME2(3) DEFAULT GETDATE(),
    so_tien_duoc_giam DECIMAL(12, 2) NOT NULL CHECK (so_tien_duoc_giam >= 0),
    CONSTRAINT FK_ApDung_PhieuGiamGia_Opt FOREIGN KEY (phieu_giam_gia_id) REFERENCES dbo.PhieuGiamGia(id),
    CONSTRAINT FK_ApDung_HoaDon_Opt FOREIGN KEY (hoa_don_id) REFERENCES dbo.HoaDon(id) ON DELETE CASCADE
);
GO
-- Index cho khóa ngoại
CREATE NONCLUSTERED INDEX IX_PhieuGiamGiaApDung_PhieuId ON dbo.PhieuGiamGiaApDung(phieu_giam_gia_id);
CREATE NONCLUSTERED INDEX IX_PhieuGiamGiaApDung_HoaDonId ON dbo.PhieuGiamGiaApDung(hoa_don_id);
GO

-- ==================================================
-- Bảng: Tham Số Hệ Thống (System Parameter)
-- ==================================================
IF OBJECT_ID('dbo.ThamSoHeThong', 'U') IS NOT NULL DROP TABLE dbo.ThamSoHeThong; GO
CREATE TABLE dbo.ThamSoHeThong (
    ma_tham_so VARCHAR(100) PRIMARY KEY,
    gia_tri NVARCHAR(255) NOT NULL,
    mo_ta NVARCHAR(500),
    kieu_du_lieu NVARCHAR(50) CHECK (kieu_du_lieu IN (N'SoNguyen', N'SoThuc', N'Chuoi', N'ThoiGianPhut', N'Boolean'))
);
GO
INSERT INTO dbo.ThamSoHeThong (ma_tham_so, gia_tri, mo_ta, kieu_du_lieu) VALUES
('THOI_GIAN_CHECKIN_TRUOC_GIO', '30', N'Số phút cho phép check-in trước giờ đá bắt đầu', 'ThoiGianPhut'),
('THOI_GIAN_CHECKIN_SAU_GIO', '15', N'Số phút cho phép check-in sau giờ đá bắt đầu (quá hạn sẽ thành Không đến)', 'ThoiGianPhut'),
('THOI_GIAN_THONG_BAO_TRUOC', '60', N'Số phút thông báo nhắc lịch cho khách trước giờ đá', 'ThoiGianPhut'),
('SO_DIEN_THOAI_HOTLINE', '0123456789', N'Số điện thoại hỗ trợ khách hàng', 'Chuoi'),
('CHO_PHEP_DOI_LICH_TRUOC_GIO', '120', N'Số phút tối thiểu trước giờ đá để khách được phép đổi lịch', 'ThoiGianPhut'),
('SO_LAN_DOI_LICH_TOI_DA', '1', N'Số lần tối đa khách được đổi lịch cho 1 lần đặt', 'SoNguyen');
GO

-- ==================================================
-- Bảng: Luật Sân (Rules)
-- ==================================================
IF OBJECT_ID('dbo.LuatSan', 'U') IS NOT NULL DROP TABLE dbo.LuatSan; GO
CREATE TABLE dbo.LuatSan (
  id INT PRIMARY KEY IDENTITY(1,1),
  tieu_de NVARCHAR(255) NOT NULL,
  noi_dung NVARCHAR(MAX) NOT NULL,
  ngay_ban_hanh DATE DEFAULT GETDATE(),
  trang_thai NVARCHAR(20) CHECK (trang_thai IN (N'HieuLuc', N'HetHieuLuc')) DEFAULT N'HieuLuc'
);
GO
INSERT INTO dbo.LuatSan(tieu_de, noi_dung) VALUES
(N'Quy định chung', N'1. Giữ gìn vệ sinh chung.<0xE3><0x80><0x80>2. Không mang đồ ăn thức uống từ bên ngoài vào.<0xE3><0x80><0x80>3. Đền bù nếu làm hư hỏng tài sản của sân theo giá niêm yết.');
GO

-- === KẾT THÚC SCHEMA OPTIMIZED ===