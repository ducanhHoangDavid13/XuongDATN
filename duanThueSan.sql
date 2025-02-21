create database DB_Dat_San
GO	
use  DB_Dat_San
go
DROP TABLE Nhan_Vien
CREATE TABLE Nhan_Vien (
ID INT PRIMARY KEY IDENTITY(1,1),
    ma_nhan_vien NVARCHAR(255) ,
    ten_nhan_vien NVARCHAR(255),
    mat_khau VARCHAR(255),
    email VARCHAR(255),
    gioi_tinh BIT,
    sdt VARCHAR(20),
    dia_chi VARCHAR(255),
    trang_thai INT
);
drop table Khach_Hang
CREATE TABLE Khach_Hang (
    id INT PRIMARY KEY IDENTITY(1,1),
    ma_khach_hang NVARCHAR(255),
    mat_khau VARCHAR(255),
    ho_va_ten NVARCHAR(255),
    email VARCHAR(255),
    gioi_tinh BIT,
    so_dien_thoai VARCHAR(20),
    trang_thai INT
);
drop table Hoa_Don
CREATE TABLE Hoa_Don (
    id INT PRIMARY KEY IDENTITY(1,1),
    id_nhan_vien INT FOREIGN KEY REFERENCES Nhan_Vien(id),
    id_khach_hang INT FOREIGN KEY REFERENCES Khach_Hang(id),
    ma_hoa_don NVARCHAR(255),
    ngay_tao DATETIME,
    tong_tien DECIMAL(18,2),
    loai_hoa_don INT,
    ghi_chu VARCHAR(255),
    trang_thai INT
);
drop table hoa_don_chi_tiet
CREATE TABLE hoa_don_chi_tiet (
    id INT PRIMARY KEY IDENTITY(1,1),
    id_san_ca INT,
    id_phieu_giam_gia INT,
    id_nhan_vien INT FOREIGN KEY REFERENCES Nhan_Vien(id),
    id_hoa_don INT FOREIGN KEY REFERENCES Hoa_Don(id),
    ma_hoa_don_chi_tiet NVARCHAR(255),
    ngay_den_san DATETIME,
    tien_giam_gia DECIMAL(18,2),
    ghi_chu VARCHAR(255),
    trang_thai INT
);

CREATE TABLE do_uong (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_do_uong NVARCHAR(255),
    don_gia FLOAT,
    so_luong INT,
    trang_thai INT
);

CREATE TABLE do_thue (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_do_thue NVARCHAR(255),
    don_gia FLOAT,
    so_luong INT,
    trang_thai INT
);

CREATE TABLE dich_vu_san_bong (
    id INT PRIMARY KEY IDENTITY(1,1),
    id_nuoc_uong INT FOREIGN KEY REFERENCES do_uong(id),
    id_do_thue INT FOREIGN KEY REFERENCES do_thue(id),
    id_hoa_don_chi_tiet INT FOREIGN KEY REFERENCES hoa_don_chi_tiet(id),
    tong_tien DECIMAL(18,2),
    trang_thai INT
);

CREATE TABLE san_ca (
    id INT PRIMARY KEY IDENTITY(1,1),
    id_ca INT FOREIGN KEY REFERENCES ca(id) ,
    id_ngay INT  FOREIGN KEY REFERENCES ngay(id),
    id_san_bong INT  FOREIGN KEY REFERENCES san_bong(id),
    gia FLOAT,
    trang_thai INT
);
CREATE TABLE ngay(
id INT PRIMARY KEY IDENTITY(1,1),
ngay_san Date,
thu_trong_tuan VARCHAR(50),
trang_thai INT
)
Drop table san_bong
CREATE TABLE san_bong (
id INT PRIMARY KEY IDENTITY(1,1),
    dia_chi NVARCHAR(255),
    trang_thai INT,
    ten_san NVARCHAR(255),
	     id_loai_san INT FOREIGN KEY REFERENCES loai_san(id)
);

CREATE TABLE loai_san (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_loai_san NVARCHAR(255),
    trang_thai INT
);
Drop table phieu_giam_gia
CREATE TABLE phieu_giam_gia (
	id int identity(1,1) primary key,
    ma_phieu_giam_gia NVARCHAR(255) ,
    ten_phieu_giam_gia NVARCHAR(255),
    so_luong INT,
    muc_giam FLOAT,
    dieu_kien_su_dung FLOAT,
    gia_tri_toi_da FLOAT,
    hinh_thuc_giam_gia BIT,
    dieu_kien_ap_dung INT,
    doi_tuong_ap_dung VARCHAR(255),
    ngay_bat_dau DATE,
    ngay_ket_thuc DATE,
    ghi_chu VARCHAR(255),
    trang_thai  INT
);
Drop table phieu_giam_gia_chi_tiet
CREATE TABLE phieu_giam_gia_chi_tiet (
    id INT PRIMARY KEY IDENTITY(1,1),
    id_khach_hang INT FOREIGN KEY REFERENCES Khach_Hang(id),
    id_phieu_giam_gia INT FOREIGN KEY REFERENCES phieu_giam_gia(id),
    trang_thai int
);

CREATE TABLE ca (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_ca NVARCHAR(255),
    thoi_gian_bat_dau DATETIME,
    thoi_gian_ket_thuc DATETIME,
    trang_thai INT
);


CREATE TABLE roles (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255)
);

CREATE TABLE Users (
    id INT PRIMARY KEY IDENTITY(1,1),
    fullname NVARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(20),
    password VARCHAR(255),
    enabled BIT
);

CREATE TABLE user_role (
    id INT PRIMARY KEY IDENTITY(1,1),
    role_id INT FOREIGN KEY REFERENCES roles(id),
    user_id INT FOREIGN KEY REFERENCES Users(id)
);
/*
INSERT INTO do_thue (ten_do_thue, don_gia, so_luong, trang_thai) VALUES
('Bóng Đá', 50.0, 20, 'Còn hàng'),
('Giày Đá Bóng', 30.0, 15, 'Còn hàng'),
('Vợt Cầu Lông', 40.0, 10, 'Còn hàng'),
('Quả Cầu Lông', 10.0, 50, 'Còn hàng'),
('Vợt Pickleball', 45.0, 8, 'Còn hàng'),
('Bóng Pickleball', 12.0, 30, 'Còn hàng'),
('Áo Thể Thao', 25.0, 20, 'Còn hàng'),
('Balo Thể Thao', 40.0, 10, 'Còn hàng'),
('Bình Nước', 18.0, 20, 'Còn hàng'),
('Dây Nhảy', 8.0, 50, 'Còn hàng');
 select * from do_thue

--du lieu nhan vien
INSERT INTO Nhan_Vien (ten_nhan_vien, mat_khau, email, gioi_tinh, sdt, dia_chi, trang_thai) VALUES
('Nguyen Van A', 'anh123@', 'a@example.com', 1, '0901234567', 'Ha Noi', 'Hoạt động'),
('Tran Thi B', 'anh123@', 'b@example.com', 0, '0987654321', 'Ho Chi Minh', 'Hoạt động'),
('Le Van C', 'anh123@', 'c@example.com', 1, '0912345678', 'Da Nang', 'Không hoạt động'),
('Pham Thi D', 'anh123@', 'd@example.com', 0, '0934567890', 'Hai Phong', 'Hoạt động'),
('Hoang Van E', 'anh123@', 'e@example.com', 1, '0945678901', 'Can Tho', 'Không hoạt động'),
('Vu Thi F', 'anh123@', 'f@example.com', 0, '0956789012', 'Hue', 'Hoạt động'),
('Nguyen Van G', 'anh123@', 'g@example.com', 1, '0967890123', 'Nha Trang', 'Hoạt động'),
('Tran Thi H', 'anh123@', 'h@example.com', 0, '0978901234', 'Vung Tau', 'Không hoạt động'),
('Le Van I', 'anh123@', 'i@example.com', 1, '0989012345', 'Da Lat', 'Hoạt động'),
('Pham Thi K', 'anh123@', 'k@example.com', 0, '0990123456', 'Quang Ninh', 'Hoạt động');
select * from Nhan_Vien
--
INSERT INTO Khach_Hang (ma_khach_hang, mat_khau, ho_va_ten, email, gioi_tinh, so_dien_thoai, trang_thai) VALUES
('KH001', '123456', 'Nguyen Van D', 'd@example.com', 1, '0909876543', 'Hoạt động'),
('KH002', 'abcdef', 'Tran Thi E', 'e@example.com', 0, '0978901234', 'Hoạt động'),
('KH003', 'qwerty', 'Le Van F', 'f@example.com', 1, '0923456789', 'Không hoạt động'),
('KH004', '123456', 'Nguyen Van D', 'd@example.com', 1, '0909876543', 'Hoạt động'),
('KH005', 'abcdef', 'Tran Thi E', 'e@example.com', 0, '0978901234', 'Hoạt động'),
('KH006', 'qwerty', 'Le Van F', 'f@example.com', 1, '0923456789', 'Không hoạt động'),
('KH007', '123456', 'Nguyen Van D', 'd@example.com', 1, '0909876543', 'Hoạt động'),
('KH008', 'abcdef', 'Tran Thi E', 'e@example.com', 0, '0978901234', 'Hoạt động'),
('KH009', 'qwerty', 'Le Van F', 'f@example.com', 1, '0923456789', 'Không hoạt động'),
('KH010', '123456', 'Nguyen Van D', 'd@example.com', 1, '0909876543', 'Hoạt động'),
('KH011', '123456', 'Nguyen Van D', 'd@example.com', 1, '0909876543', 'Hoạt động'),
('KH012', 'abcdef', 'Tran Thi E', 'e@example.com', 0, '0978901234', 'Hoạt động'),
('KH013', 'qwerty', 'Le Van F', 'f@example.com', 1, '0923456789', 'Không hoạt động'),
('KH014', 'password28', 'Khach Hang 28', 'kh28@example.com', 1, '0912345658', 'Hoạt động'),
('KH015', 'password29', 'Khach Hang 29', 'kh29@example.com', 0, '0912345659', 'Hoạt động'),
('KH016', 'password30', 'Khach Hang 30', 'kh30@example.com', 1, '0912345660', 'Không hoạt động');
select * from Khach_Hang
-- do_uong
INSERT INTO do_uong (ten_do_uong, don_gia, so_luong, trang_thai) VALUES
('Coca Cola', 10000, 100, 'Còn hàng'),
('Pepsi', 10000, 50, 'Còn hàng'),
('Sprite', 12000, 200, 'Hết hàng'),
('Fanta', 12000, 150, 'Còn hàng'),
('7 Up', 11000, 100, 'Hết hàng'),
('Mirinda', 11000, 250, 'Còn hàng'),
('Sting', 15000, 80, 'Còn hàng'),
('Number 1', 15000, 120, 'Hết hàng'),
('Aquafina', 8000, 300, 'Còn hàng'),
('Lavie', 8000, 200, 'Còn hàng'),
('Coca Cola Light', 10000, 70, 'Còn hàng'),
('Pepsi Light', 10000, 90, 'Còn hàng'),
('Sprite Zero', 12000, 180, 'Hết hàng'),
('Fanta Cam', 12000, 130, 'Còn hàng'),
('7 Up Revive', 11000, 110, 'Hết hàng'),
('Mirinda Xá Xị', 11000, 220, 'Còn hàng'),
('Sting Dâu', 15000, 90, 'Còn hàng'),
('Number 1 Dâu', 15000, 150, 'Hết hàng'),
('Aquafina Chai Lớn', 8000, 250, 'Còn hàng'),
('Lavie Premium', 8000, 180, 'Còn hàng');
select * from do_uong
-- do_thue
INSERT INTO do_thue (ten_do_thue, don_gia, so_luong, trang_thai) VALUES
('Áo thun thể thao', 20000, 50, 'Còn hàng'),
('Quần short thể thao', 15000, 30, 'Còn hàng'),
('Giày thể thao', 25000, 20, 'Hết hàng'),
('Vớ thể thao', 5000, 100, 'Còn hàng'),
('Băng đô', 10000, 50, 'Hết hàng'),
('Khăn thể thao', 12000, 80, 'Còn hàng'),
('Balo thể thao', 30000, 15, 'Còn hàng'),
('Mũ thể thao', 18000, 60, 'Hết hàng'),
('Găng tay thể thao', 15000, 40, 'Còn hàng'),
('Bình nước thể thao', 10000, 90, 'Còn hàng'),
('Áo khoác thể thao', 40000, 25, 'Còn hàng'),
('Quần dài thể thao', 35000, 35, 'Còn hàng'),
('Giày đá bóng', 50000, 10, 'Hết hàng'),
('Áo đá bóng', 30000, 45, 'Còn hàng'),
('Quần đá bóng', 25000, 55, 'Còn hàng'),
('Tất đá bóng', 8000, 120, 'Còn hàng'),
('Băng bảo vệ đầu gối', 12000, 70, 'Còn hàng'),
('Băng bảo vệ cổ tay', 10000, 80, 'Còn hàng'),
('Kính thể thao', 20000, 30, 'Còn hàng'),
('Túi đựng đồ thể thao', 25000, 20, 'Còn hàng');
select * from do_thue
-- dich_vu_san_bong
INSERT INTO dich_vu_san_bong (id_nuoc_uong, id_do_thue, id_hoa_don_chi_tiet, tong_tien, trang_thai) VALUES
(1, 1, 1, 35000, 'Sẵn sàng'),
(2, 2, 2, 27000, 'Không khả dụng'),
(3, 3, 3, 42000, 'Sẵn sàng'),
(4, 4, 4, 30000, 'Sẵn sàng'),
(5, 5, 5, 25000, 'Không khả dụng'),
(6, 6, 6, 38000, 'Sẵn sàng'),
(7, 7, 7, 45000, 'Sẵn sàng'),
(8, 8, 8, 32000, 'Không khả dụng'),
(9, 9, 9, 28000, 'Sẵn sàng'),
(10, 10, 10, 40000, 'Sẵn sàng'),
(11, 11, 11, 50000, 'Sẵn sàng'),
(12, 12, 12, 37000, 'Không khả dụng'),
(13, 13, 13, 29000, 'Sẵn sàng'),
(14, 14, 14, 43000, 'Sẵn sàng'),
(15, 15, 15, 33000, 'Không khả dụng'),
(16, 16, 16, 26000, 'Sẵn sàng'),
(17, 17, 17, 39000, 'Sẵn sàng'),
(18, 18, 18, 48000, 'Không khả dụng'),
(19, 19, 19, 31000, 'Sẵn sàng'),
(20, 20, 20, 46000, 'Sẵn sàng');
select * from dich_vu_san_bong
-- san_ca
INSERT INTO san_ca (id_ca, ngay_trong_tuan, id_san_bong, gia, trang_thai) VALUES
(1, 1, 1, 150000, 'Còn trống'),
(2, 2, 2, 200000, 'Đã đặt'),
(3, 3, 3, 180000, 'Hủy'),
(4, 4, 1, 160000, 'Còn trống'),
(5, 5, 2, 220000, 'Đã đặt'),
(6, 6, 3, 190000, 'Hủy'),
(7, 7, 1, 170000, 'Còn trống'),
(8, 1, 2, 210000, 'Đã đặt'),
(9, 2, 3, 200000, 'Hủy'),
(10, 3, 1, 155000, 'Còn trống'),
(11, 4, 2, 230000, 'Đã đặt'),
(12, 5, 3, 185000, 'Hủy'),
(13, 6, 1, 165000, 'Còn trống'),
(14, 7, 2, 215000, 'Đã đặt'),
(15, 1, 3, 195000, 'Hủy'),
(16, 2, 1, 175000, 'Còn trống'),
(17, 3, 2, 225000,'Hủy')*/
