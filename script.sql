USE [master]
GO
/****** Object:  Database [DB_Dat_San]    Script Date: 2/21/2025 2:16:43 PM ******/
CREATE DATABASE [DB_Dat_San]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DB_Dat_San', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\DB_Dat_San.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DB_Dat_San_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\DB_Dat_San_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [DB_Dat_San] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DB_Dat_San].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DB_Dat_San] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DB_Dat_San] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DB_Dat_San] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DB_Dat_San] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DB_Dat_San] SET ARITHABORT OFF 
GO
ALTER DATABASE [DB_Dat_San] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DB_Dat_San] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DB_Dat_San] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DB_Dat_San] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DB_Dat_San] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DB_Dat_San] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DB_Dat_San] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DB_Dat_San] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DB_Dat_San] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DB_Dat_San] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DB_Dat_San] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DB_Dat_San] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DB_Dat_San] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DB_Dat_San] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DB_Dat_San] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DB_Dat_San] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DB_Dat_San] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DB_Dat_San] SET RECOVERY FULL 
GO
ALTER DATABASE [DB_Dat_San] SET  MULTI_USER 
GO
ALTER DATABASE [DB_Dat_San] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DB_Dat_San] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DB_Dat_San] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DB_Dat_San] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DB_Dat_San] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DB_Dat_San] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'DB_Dat_San', N'ON'
GO
ALTER DATABASE [DB_Dat_San] SET QUERY_STORE = ON
GO
ALTER DATABASE [DB_Dat_San] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [DB_Dat_San]
GO
/****** Object:  Table [dbo].[ca]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ca](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_ca] [nvarchar](255) NULL,
	[thoi_gian_bat_dau] [datetime] NULL,
	[thoi_gian_ket_thuc] [datetime] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[dich_vu_san_bong]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dich_vu_san_bong](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_nuoc_uong] [int] NULL,
	[id_do_thue] [int] NULL,
	[id_hoa_don_chi_tiet] [int] NULL,
	[tong_tien] [decimal](18, 2) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[do_thue]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[do_thue](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_do_thue] [nvarchar](255) NULL,
	[don_gia] [float] NULL,
	[so_luong] [int] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[do_uong]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[do_uong](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_do_uong] [nvarchar](255) NULL,
	[don_gia] [float] NULL,
	[so_luong] [int] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Hoa_Don]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Hoa_Don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_nhan_vien] [int] NULL,
	[id_khach_hang] [int] NULL,
	[ma_hoa_don] [nvarchar](255) NULL,
	[ngay_tao] [datetime] NULL,
	[tong_tien] [decimal](18, 2) NULL,
	[loai_hoa_don] [int] NULL,
	[ghi_chu] [varchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don_chi_tiet]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don_chi_tiet](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_san_ca] [int] NULL,
	[id_phieu_giam_gia] [int] NULL,
	[id_nhan_vien] [int] NULL,
	[id_hoa_don] [int] NULL,
	[ma_hoa_don_chi_tiet] [nvarchar](255) NULL,
	[ngay_den_san] [datetime] NULL,
	[tien_giam_gia] [decimal](18, 2) NULL,
	[ghi_chu] [varchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Khach_Hang]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Khach_Hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_khach_hang] [nvarchar](255) NULL,
	[mat_khau] [varchar](255) NULL,
	[ho_va_ten] [nvarchar](255) NULL,
	[email] [varchar](255) NULL,
	[gioi_tinh] [bit] NULL,
	[so_dien_thoai] [varchar](20) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[loai_san]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[loai_san](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_loai_san] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ngay]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ngay](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ngay_san] [date] NULL,
	[thu_trong_tuan] [varchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Nhan_Vien]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Nhan_Vien](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ma_nhan_vien] [nvarchar](255) NULL,
	[ten_nhan_vien] [nvarchar](255) NULL,
	[mat_khau] [varchar](255) NULL,
	[email] [varchar](255) NULL,
	[gioi_tinh] [bit] NULL,
	[sdt] [varchar](20) NULL,
	[dia_chi] [varchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phieu_giam_gia]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phieu_giam_gia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_phieu_giam_gia] [nvarchar](255) NULL,
	[ten_phieu_giam_gia] [nvarchar](255) NULL,
	[so_luong] [int] NULL,
	[muc_giam] [float] NULL,
	[dieu_kien_su_dung] [float] NULL,
	[gia_tri_toi_da] [float] NULL,
	[hinh_thuc_giam_gia] [bit] NULL,
	[dieu_kien_ap_dung] [int] NULL,
	[doi_tuong_ap_dung] [varchar](255) NULL,
	[ngay_bat_dau] [date] NULL,
	[ngay_ket_thuc] [date] NULL,
	[ghi_chu] [varchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phieu_giam_gia_chi_tiet]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phieu_giam_gia_chi_tiet](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_khach_hang] [int] NULL,
	[id_phieu_giam_gia] [int] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_bong]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_bong](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dia_chi] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
	[ten_san] [nvarchar](255) NULL,
	[id_loai_san] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_ca]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_ca](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_ca] [int] NULL,
	[id_ngay] [int] NULL,
	[id_san_bong] [int] NULL,
	[gia] [float] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_role]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NULL,
	[user_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 2/21/2025 2:16:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fullname] [nvarchar](255) NULL,
	[email] [varchar](255) NULL,
	[phone_number] [varchar](20) NULL,
	[password] [varchar](255) NULL,
	[enabled] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[ca] ON 

INSERT [dbo].[ca] ([id], [ten_ca], [thoi_gian_bat_dau], [thoi_gian_ket_thuc], [trang_thai]) VALUES (1, N'Ca Sáng', CAST(N'2025-02-21T08:00:00.000' AS DateTime), CAST(N'2025-02-21T12:00:00.000' AS DateTime), 1)
INSERT [dbo].[ca] ([id], [ten_ca], [thoi_gian_bat_dau], [thoi_gian_ket_thuc], [trang_thai]) VALUES (2, N'Ca Chiều', CAST(N'2025-02-21T13:00:00.000' AS DateTime), CAST(N'2025-02-21T17:00:00.000' AS DateTime), 1)
SET IDENTITY_INSERT [dbo].[ca] OFF
GO
SET IDENTITY_INSERT [dbo].[dich_vu_san_bong] ON 

INSERT [dbo].[dich_vu_san_bong] ([id], [id_nuoc_uong], [id_do_thue], [id_hoa_don_chi_tiet], [tong_tien], [trang_thai]) VALUES (1, 1, 1, 1, CAST(60000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[dich_vu_san_bong] ([id], [id_nuoc_uong], [id_do_thue], [id_hoa_don_chi_tiet], [tong_tien], [trang_thai]) VALUES (2, 2, 2, 2, CAST(90000.00 AS Decimal(18, 2)), 1)
SET IDENTITY_INSERT [dbo].[dich_vu_san_bong] OFF
GO
SET IDENTITY_INSERT [dbo].[do_thue] ON 

INSERT [dbo].[do_thue] ([id], [ten_do_thue], [don_gia], [so_luong], [trang_thai]) VALUES (1, N'Áo đấu', 50000, 20, 1)
INSERT [dbo].[do_thue] ([id], [ten_do_thue], [don_gia], [so_luong], [trang_thai]) VALUES (2, N'Giày đá bóng', 70000, 10, 1)
SET IDENTITY_INSERT [dbo].[do_thue] OFF
GO
SET IDENTITY_INSERT [dbo].[do_uong] ON 

INSERT [dbo].[do_uong] ([id], [ten_do_uong], [don_gia], [so_luong], [trang_thai]) VALUES (1, N'Nước suối', 10000, 50, 1)
INSERT [dbo].[do_uong] ([id], [ten_do_uong], [don_gia], [so_luong], [trang_thai]) VALUES (2, N'Nước tăng lực', 15000, 30, 1)
SET IDENTITY_INSERT [dbo].[do_uong] OFF
GO
SET IDENTITY_INSERT [dbo].[Hoa_Don] ON 

INSERT [dbo].[Hoa_Don] ([id], [id_nhan_vien], [id_khach_hang], [ma_hoa_don], [ngay_tao], [tong_tien], [loai_hoa_don], [ghi_chu], [trang_thai]) VALUES (1, 1, 1, N'HD001', CAST(N'2025-02-20T00:00:00.000' AS DateTime), CAST(1000000.00 AS Decimal(18, 2)), 1, N'Ð?t sân bu?i sáng', NULL)
INSERT [dbo].[Hoa_Don] ([id], [id_nhan_vien], [id_khach_hang], [ma_hoa_don], [ngay_tao], [tong_tien], [loai_hoa_don], [ghi_chu], [trang_thai]) VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, N'Ð?t sân bu?i t?i', NULL)
INSERT [dbo].[Hoa_Don] ([id], [id_nhan_vien], [id_khach_hang], [ma_hoa_don], [ngay_tao], [tong_tien], [loai_hoa_don], [ghi_chu], [trang_thai]) VALUES (3, 1, 1, N'HD001', CAST(N'2025-02-20T00:00:00.000' AS DateTime), CAST(1000000.00 AS Decimal(18, 2)), 1, N'Ð?t sân bu?i sáng', 1)
INSERT [dbo].[Hoa_Don] ([id], [id_nhan_vien], [id_khach_hang], [ma_hoa_don], [ngay_tao], [tong_tien], [loai_hoa_don], [ghi_chu], [trang_thai]) VALUES (4, 2, 2, N'HD002', CAST(N'2025-02-21T00:00:00.000' AS DateTime), CAST(1200000.00 AS Decimal(18, 2)), 2, N'Ð?t sân bu?i t?i', 1)
SET IDENTITY_INSERT [dbo].[Hoa_Don] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don_chi_tiet] ON 

INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_san_ca], [id_phieu_giam_gia], [id_nhan_vien], [id_hoa_don], [ma_hoa_don_chi_tiet], [ngay_den_san], [tien_giam_gia], [ghi_chu], [trang_thai]) VALUES (1, 1, 1, 1, 1, N'HDCT001', CAST(N'2025-02-21T08:00:00.000' AS DateTime), CAST(50000.00 AS Decimal(18, 2)), N'Gi?m giá thành viên', 1)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_san_ca], [id_phieu_giam_gia], [id_nhan_vien], [id_hoa_don], [ma_hoa_don_chi_tiet], [ngay_den_san], [tien_giam_gia], [ghi_chu], [trang_thai]) VALUES (2, 2, 2, 2, 2, N'HDCT002', CAST(N'2025-02-21T18:00:00.000' AS DateTime), CAST(70000.00 AS Decimal(18, 2)), N'Uu dãi d?c bi?t', 1)
SET IDENTITY_INSERT [dbo].[hoa_don_chi_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[Khach_Hang] ON 

INSERT [dbo].[Khach_Hang] ([id], [ma_khach_hang], [mat_khau], [ho_va_ten], [email], [gioi_tinh], [so_dien_thoai], [trang_thai]) VALUES (1, N'KH001', N'khpass1', N'Phạm Văn C', N'phamvanc@example.com', 1, N'0321654987', 1)
INSERT [dbo].[Khach_Hang] ([id], [ma_khach_hang], [mat_khau], [ho_va_ten], [email], [gioi_tinh], [so_dien_thoai], [trang_thai]) VALUES (2, N'KH002', N'khpass2', N'Ngô Thị D', N'ngothid@example.com', 0, N'0398765432', 1)
SET IDENTITY_INSERT [dbo].[Khach_Hang] OFF
GO
SET IDENTITY_INSERT [dbo].[loai_san] ON 

INSERT [dbo].[loai_san] ([id], [ten_loai_san], [trang_thai]) VALUES (1, N'Sân cỏ nhân tạo', 1)
INSERT [dbo].[loai_san] ([id], [ten_loai_san], [trang_thai]) VALUES (2, N'Sân cỏ tự nhiên', 1)
INSERT [dbo].[loai_san] ([id], [ten_loai_san], [trang_thai]) VALUES (3, N'Sân đánh trong nhà', 1)
INSERT [dbo].[loai_san] ([id], [ten_loai_san], [trang_thai]) VALUES (4, N'Sân đánh ngoài trời', 1)
SET IDENTITY_INSERT [dbo].[loai_san] OFF
GO
SET IDENTITY_INSERT [dbo].[ngay] ON 

INSERT [dbo].[ngay] ([id], [ngay_san], [thu_trong_tuan], [trang_thai]) VALUES (1, CAST(N'2025-02-21' AS Date), N'Th? Sáu', 1)
INSERT [dbo].[ngay] ([id], [ngay_san], [thu_trong_tuan], [trang_thai]) VALUES (2, CAST(N'2025-02-22' AS Date), N'Th? B?y', 1)
SET IDENTITY_INSERT [dbo].[ngay] OFF
GO
SET IDENTITY_INSERT [dbo].[Nhan_Vien] ON 

INSERT [dbo].[Nhan_Vien] ([ID], [ma_nhan_vien], [ten_nhan_vien], [mat_khau], [email], [gioi_tinh], [sdt], [dia_chi], [trang_thai]) VALUES (1, N'NV001', N'Lê Minh E', N'pass123', N'leminhe@example.com', 1, N'0912345678', N'Ha Noi', 1)
INSERT [dbo].[Nhan_Vien] ([ID], [ma_nhan_vien], [ten_nhan_vien], [mat_khau], [email], [gioi_tinh], [sdt], [dia_chi], [trang_thai]) VALUES (2, N'NV002', N'Hoàng An F', N'pass456', N'hoanganf@example.com', 0, N'0987654321', N'Hai phong', 0)
SET IDENTITY_INSERT [dbo].[Nhan_Vien] OFF
GO
SET IDENTITY_INSERT [dbo].[phieu_giam_gia] ON 

INSERT [dbo].[phieu_giam_gia] ([id], [ma_phieu_giam_gia], [ten_phieu_giam_gia], [so_luong], [muc_giam], [dieu_kien_su_dung], [gia_tri_toi_da], [hinh_thuc_giam_gia], [dieu_kien_ap_dung], [doi_tuong_ap_dung], [ngay_bat_dau], [ngay_ket_thuc], [ghi_chu], [trang_thai]) VALUES (1, N'PGG202501', N'Giảm 10% tổng hóa đơn', 20, 10, 500000, 100000, 1, 1, N'T?t c? khách hàng', CAST(N'2025-02-20' AS Date), CAST(N'2025-02-28' AS Date), N'Áp d?ng cho hóa don t? 500k', 1)
INSERT [dbo].[phieu_giam_gia] ([id], [ma_phieu_giam_gia], [ten_phieu_giam_gia], [so_luong], [muc_giam], [dieu_kien_su_dung], [gia_tri_toi_da], [hinh_thuc_giam_gia], [dieu_kien_ap_dung], [doi_tuong_ap_dung], [ngay_bat_dau], [ngay_ket_thuc], [ghi_chu], [trang_thai]) VALUES (2, N'PGG202502', N'Giảm 50K cho hóa đơn trên 200K', 10, 50000, 200000, 50000, 1, 2, N'Khách hàng m?i', CAST(N'2025-03-01' AS Date), CAST(N'2025-03-15' AS Date), N'Ch? áp d?ng cho l?n d?t sân d?u tiên', 1)
SET IDENTITY_INSERT [dbo].[phieu_giam_gia] OFF
GO
SET IDENTITY_INSERT [dbo].[phieu_giam_gia_chi_tiet] ON 

INSERT [dbo].[phieu_giam_gia_chi_tiet] ([id], [id_khach_hang], [id_phieu_giam_gia], [trang_thai]) VALUES (1, 1, 1, 1)
INSERT [dbo].[phieu_giam_gia_chi_tiet] ([id], [id_khach_hang], [id_phieu_giam_gia], [trang_thai]) VALUES (2, 2, 2, 1)
SET IDENTITY_INSERT [dbo].[phieu_giam_gia_chi_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[roles] ON 

INSERT [dbo].[roles] ([id], [name]) VALUES (1, N'Admin')
INSERT [dbo].[roles] ([id], [name]) VALUES (2, N'Nhân viên')
INSERT [dbo].[roles] ([id], [name]) VALUES (3, N'Khách hàng')
SET IDENTITY_INSERT [dbo].[roles] OFF
GO
SET IDENTITY_INSERT [dbo].[san_bong] ON 

INSERT [dbo].[san_bong] ([id], [dia_chi], [trang_thai], [ten_san], [id_loai_san]) VALUES (1, N'123 Nguyễn Trãi', 1, N'Sân A', 1)
INSERT [dbo].[san_bong] ([id], [dia_chi], [trang_thai], [ten_san], [id_loai_san]) VALUES (2, N'456 Trần Hưng Đạo', 1, N'San B', 4)
SET IDENTITY_INSERT [dbo].[san_bong] OFF
GO
SET IDENTITY_INSERT [dbo].[san_ca] ON 

INSERT [dbo].[san_ca] ([id], [id_ca], [id_ngay], [id_san_bong], [gia], [trang_thai]) VALUES (1, 1, 1, 1, 500000, 1)
INSERT [dbo].[san_ca] ([id], [id_ca], [id_ngay], [id_san_bong], [gia], [trang_thai]) VALUES (2, 2, 2, 2, 700000, 1)
SET IDENTITY_INSERT [dbo].[san_ca] OFF
GO
SET IDENTITY_INSERT [dbo].[user_role] ON 

INSERT [dbo].[user_role] ([id], [role_id], [user_id]) VALUES (1, 1, 1)
INSERT [dbo].[user_role] ([id], [role_id], [user_id]) VALUES (2, 2, 2)
INSERT [dbo].[user_role] ([id], [role_id], [user_id]) VALUES (4, 3, 3)
SET IDENTITY_INSERT [dbo].[user_role] OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([id], [fullname], [email], [phone_number], [password], [enabled]) VALUES (1, N'Nguyễn Văn A', N'nguyenvana@example.com', N'0123456789', N'password123', 1)
INSERT [dbo].[Users] ([id], [fullname], [email], [phone_number], [password], [enabled]) VALUES (2, N'Trần Thị B', N'tranthib@example.com', N'0987654321', N'password456', 1)
INSERT [dbo].[Users] ([id], [fullname], [email], [phone_number], [password], [enabled]) VALUES (3, N'Trần Thị C', N'tranthic@example.com', N'0921312313', N'password456', 0)
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
ALTER TABLE [dbo].[dich_vu_san_bong]  WITH CHECK ADD FOREIGN KEY([id_do_thue])
REFERENCES [dbo].[do_thue] ([id])
GO
ALTER TABLE [dbo].[dich_vu_san_bong]  WITH CHECK ADD FOREIGN KEY([id_hoa_don_chi_tiet])
REFERENCES [dbo].[hoa_don_chi_tiet] ([id])
GO
ALTER TABLE [dbo].[dich_vu_san_bong]  WITH CHECK ADD FOREIGN KEY([id_nuoc_uong])
REFERENCES [dbo].[do_uong] ([id])
GO
ALTER TABLE [dbo].[Hoa_Don]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[Khach_Hang] ([id])
GO
ALTER TABLE [dbo].[Hoa_Don]  WITH CHECK ADD FOREIGN KEY([id_nhan_vien])
REFERENCES [dbo].[Nhan_Vien] ([ID])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[Hoa_Don] ([id])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_nhan_vien])
REFERENCES [dbo].[Nhan_Vien] ([ID])
GO
ALTER TABLE [dbo].[phieu_giam_gia_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[Khach_Hang] ([id])
GO
ALTER TABLE [dbo].[phieu_giam_gia_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_phieu_giam_gia])
REFERENCES [dbo].[phieu_giam_gia] ([id])
GO
ALTER TABLE [dbo].[san_bong]  WITH CHECK ADD FOREIGN KEY([id_loai_san])
REFERENCES [dbo].[loai_san] ([id])
GO
ALTER TABLE [dbo].[san_ca]  WITH CHECK ADD FOREIGN KEY([id_ca])
REFERENCES [dbo].[ca] ([id])
GO
ALTER TABLE [dbo].[san_ca]  WITH CHECK ADD FOREIGN KEY([id_ngay])
REFERENCES [dbo].[ngay] ([id])
GO
ALTER TABLE [dbo].[san_ca]  WITH CHECK ADD FOREIGN KEY([id_san_bong])
REFERENCES [dbo].[san_bong] ([id])
GO
ALTER TABLE [dbo].[user_role]  WITH CHECK ADD FOREIGN KEY([role_id])
REFERENCES [dbo].[roles] ([id])
GO
ALTER TABLE [dbo].[user_role]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[Users] ([id])
GO
USE [master]
GO
ALTER DATABASE [DB_Dat_San] SET  READ_WRITE 
GO
