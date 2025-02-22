<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Sân Sóng 24/7</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/view/css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
</head>
<body class="container">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<header>
    <div class="container">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/">
                <img src="https://th.bing.com/th/id/OIP.g8p9wGQ-lsQI_slSWsZZagHaHz?rs=1&pid=ImgDetMain" alt="Sport World - Heart of the Game" width="200" height="100">
            </a>
        </div>
        <div class="search">
            <input type="text" placeholder="Tìm sân">
            <button type="submit">Tìm kiếm</button>
        </div>
        <div class="user-actions">
            <a href="#"><i class="bi bi-shop"></i> <span>Dịch vụ</span></a>
            <a href="#"><i class="bi bi-shop"></i> <span>Cửa hàng</span></a>
            <a href="#"><i class="bi bi-football"></i> <span>Sân thể thao</span></a>
            <a href="#"><i class="bi bi-people"></i> <span>Tài khoản</span></a>
            <a href="#"><i class="bi bi-cart3"></i> <span>Giỏ hàng</span></a>
        </div>
    </div>
</header>
<nav>
    <ul>
        <li><a href="#">Bóng đá</a></li>
        <li><a href="#">Cầu lông</a></li>
        <li><a href="#">Pickleball</a></li>
        <li><a href="#">Bóng rổ</a></li>
        <li><a href="#">Tennis</a></li>
        <li><a href="#">Bóng chuyền</a></li>
    </ul>
</nav>
<main>
    <section class="breadcrumb">
        <a href="#">Trang chủ</a> > <a href="#">Bóng đá</a>
    </section>

    <section class="category-title">
        <h1>Bóng đá</h1>
    </section>

    <section class="product-categories">
        <div class="category-item">
            <img src="https://th.bing.com/th/id/OIP.ekLea_Zoy_wvjdyHOGi_7wHaD4?rs=1&pid=ImgDetMain" alt="Sân bóng 7 người">
            <h3>Sân bóng 7 người</h3>
            <p>24 Sản phẩm</p>
        </div>
        <div class="category-item">
            <img src="https://drive.gianhangvn.com/image/nha-thi-dau-cau-long-1159854j25005.jpgt" alt="Sân cầu lông trong nhà">
            <h3>Sân cầu lông trong nhà</h3>
            <p>4 Sản phẩm</p>
        </div>
    </section>
</main>
<footer>
    <div class="footer-container">
        <div class="footer-section">
            <h4>Chính sách</h4>
            <ul>
                <li><a href="#">Chính sách bảo mật</a></li>
                <li><a href="#">Điều khoản sử dụng</a></li>
                <li><a href="#">Hướng dẫn đổi trả</a></li>
            </ul>
        </div>
        <div class="footer-section">
            <h4>Hỗ trợ</h4>
            <p>Hotline: 0904795885</p>
            <p>Email: <a href="mailto:anhducfpoly2022@gmail.com">anhducfpoly2022@gmail.com</a></p>
        </div>
    </div>
</footer>
</body>
</html>
