package com.example.chatbotai.service;

import com.example.chatbotai.Entity.ChiTietHocPhi;
import com.example.chatbotai.Entity.HocPhi;
import com.example.chatbotai.Entity.ThanhToan;
import com.example.chatbotai.dto.requests.XacNhanThanhToanRequest;
import com.example.chatbotai.dto.response.*;
import com.example.chatbotai.repository.ChiTietHocPhiRepository;
import com.example.chatbotai.repository.HocPhiRepository;
import com.example.chatbotai.repository.ThanhToanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HocPhiService {
    
    private final HocPhiRepository hocPhiRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final ChiTietHocPhiRepository chiTietHocPhiRepository;
    private final VietQRService vietQRService;
    
    public HocPhiResponse getHocPhiByMaSV(String maSV) {
        List<HocPhi> hocPhiList = hocPhiRepository.findByMaSV(maSV);
        List<ThanhToan> thanhToanList = thanhToanRepository.findByMaSV(maSV);
        List<ChiTietHocPhi> chiTietList = chiTietHocPhiRepository.findByMaSV(maSV);
        
        // Tính tổng tiền đã nộp
        BigDecimal tongDaNop = thanhToanList.stream()
                .map(ThanhToan::getSoTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Tính tổng tiền chưa nộp
        BigDecimal tongChuaNop = hocPhiList.stream()
                .filter(hp -> "Chưa nộp".equals(hp.getTrangThai()))
                .map(HocPhi::getSoTienPhaiNop)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Danh sách học phí theo học kỳ
        List<HocPhiHocKyResponse> danhSachHocPhi = hocPhiList.stream()
                .map(this::convertToHocPhiHocKy)
                .collect(Collectors.toList());
        
        // Danh sách hóa đơn
        List<HoaDonResponse> danhSachHoaDon = thanhToanList.stream()
                .map(this::convertToHoaDon)
                .collect(Collectors.toList());
        
        // Chi tiết các khoản đã nộp (từ ChiTietHocPhi)
        List<ChiTietThanhToanResponse> chiTietDaNop = chiTietList.stream()
                .map(this::convertToChiTiet)
                .collect(Collectors.toList());
        
        return HocPhiResponse.builder()
                .tongTienDaNop(tongDaNop)
                .tongTienChuaNop(tongChuaNop)
                .danhSachHocPhi(danhSachHocPhi)
                .danhSachHoaDon(danhSachHoaDon)
                .chiTietDaNop(chiTietDaNop)
                .build();
    }
    
    public ThanhToanHocKyResponse getThanhToanByHocKy(String maSV, String maHocKy, String namHoc) {
        // Lấy thông tin học phí để kiểm tra trạng thái
        HocPhi hocPhi = hocPhiRepository.findByMaSVAndHocKyAndNamHoc(maSV, maHocKy, namHoc);
        
        if (hocPhi == null) {
            throw new RuntimeException("Không tìm thấy thông tin học phí cho học kỳ này");
        }
        
        String trangThai = hocPhi.getTrangThai();
        boolean daNop = "Đã nộp".equalsIgnoreCase(trangThai);
        
        List<ChiTietHocPhi> chiTietList = chiTietHocPhiRepository.findByMaSVAndHocKyAndNamHoc(maSV, maHocKy, namHoc);
        
        // Tính tổng
        BigDecimal tongTienPhaiNop = BigDecimal.ZERO;
        BigDecimal tongMienGiam = BigDecimal.ZERO;
        BigDecimal tongThucNop = BigDecimal.ZERO;
        
        List<ThanhToanHocKyResponse.ChiTietMonHocResponse> chiTietMonHoc = null;
        
        // Chỉ hiển thị chi tiết môn học khi chưa nộp
        if (!daNop) {
            chiTietMonHoc = chiTietList.stream()
                    .map(ct -> {
                        BigDecimal soTienNop = ct.getDonGia() != null ? ct.getDonGia() : BigDecimal.ZERO;
                        BigDecimal mienGiam = ct.getMienGiam() != null ? ct.getMienGiam() : BigDecimal.ZERO;
                        BigDecimal soTienThucNop = BigDecimal.ZERO;
                        
                        if (ct.getSoTinChi() != null && ct.getDonGia() != null) {
                            soTienThucNop = ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoTinChi()));
                        }
                        
                        return ThanhToanHocKyResponse.ChiTietMonHocResponse.builder()
                                .tenHocPhan(ct.getLopHocPhan() != null && ct.getLopHocPhan().getMonHoc() != null 
                                        ? ct.getLopHocPhan().getMonHoc().getTenMon() : null)
                                .soTinChi(ct.getSoTinChi())
                                .soTienNop(soTienNop)
                                .mienGiam(mienGiam)
                                .soTienThucNop(soTienThucNop)
                                .maLopHP(ct.getLopHocPhan() != null ? ct.getLopHocPhan().getMaLopHP() : null)
                                .build();
                    })
                    .collect(Collectors.toList());
        }
        
        // Tính tổng tiền
        for (ChiTietHocPhi ct : chiTietList) {
            if (ct.getSoTinChi() != null && ct.getDonGia() != null) {
                BigDecimal tienNop = ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoTinChi()));
                tongTienPhaiNop = tongTienPhaiNop.add(tienNop);
                tongThucNop = tongThucNop.add(tienNop);
            }
            if (ct.getMienGiam() != null) {
                tongMienGiam = tongMienGiam.add(ct.getMienGiam());
            }
        }
        
        // Lấy thông tin học kỳ
        Integer hocKy = null;
        if (maHocKy != null && maHocKy.length() > 0) {
            try {
                hocKy = Integer.parseInt(maHocKy.substring(maHocKy.length() - 1));
            } catch (NumberFormatException e) {
                hocKy = null;
            }
        }
        
        // Tạo QR code URL và thông tin chuyển khoản nếu chưa nộp
        String qrCodeUrl = null;
        ThanhToanHocKyResponse.ThongTinChuyenKhoan thongTinChuyenKhoan = null;
        
        if (!daNop && tongThucNop.compareTo(BigDecimal.ZERO) > 0) {
            long amount = tongThucNop.longValue();
            
            // Tạo QR code URL
            qrCodeUrl = vietQRService.createBankTransferQR(maSV, amount, maHocKy, namHoc);
            
            // Tạo thông tin chuyển khoản
            VietQRService.BankTransferInfo bankInfo = vietQRService.getBankTransferInfo(maSV, amount, maHocKy, namHoc);
            thongTinChuyenKhoan = ThanhToanHocKyResponse.ThongTinChuyenKhoan.builder()
                    .nganHang(bankInfo.getBankName())
                    .soTaiKhoan(bankInfo.getAccountNumber())
                    .tenTaiKhoan(bankInfo.getAccountName())
                    .soTien(bankInfo.getAmount())
                    .noiDung(bankInfo.getDescription())
                    .build();
        }
        
        return ThanhToanHocKyResponse.builder()
                .namHoc(namHoc)
                .hocKy(hocKy)
                .trangThai(trangThai)
                .tongTienPhaiNop(tongTienPhaiNop)
                .tongMienGiam(tongMienGiam)
                .tongThucNop(tongThucNop)
                .qrCodeUrl(qrCodeUrl)
                .thongTinChuyenKhoan(thongTinChuyenKhoan)
                .chiTietMonHoc(chiTietMonHoc)
                .build();
    }
    
    private HocPhiHocKyResponse convertToHocPhiHocKy(HocPhi hocPhi) {
        HocPhiHocKyResponse.HocPhiHocKyResponseBuilder builder = HocPhiHocKyResponse.builder();
        
        if (hocPhi.getHocKy() != null) {
            builder.namHoc(hocPhi.getHocKy().getNamHoc());
            String maHocKy = hocPhi.getHocKy().getMaHocKy();
            if (maHocKy != null && maHocKy.length() > 0) {
                try {
                    builder.hocKy(Integer.parseInt(maHocKy.substring(maHocKy.length() - 1)));
                } catch (NumberFormatException e) {
                    builder.hocKy(null);
                }
            }
        }
        
        builder.mucHocPhi(hocPhi.getTongTien());
        builder.mienGiam(hocPhi.getMienGiam());
        builder.soTienPhaiNop(hocPhi.getSoTienPhaiNop());
        builder.trangThai(hocPhi.getTrangThai());
        
        return builder.build();
    }
    
    private HoaDonResponse convertToHoaDon(ThanhToan thanhToan) {
        HoaDonResponse.HoaDonResponseBuilder builder = HoaDonResponse.builder();
        
        if (thanhToan.getHocPhi() != null && thanhToan.getHocPhi().getHocKy() != null) {
            builder.namHoc(thanhToan.getHocPhi().getHocKy().getNamHoc());
            String maHocKy = thanhToan.getHocPhi().getHocKy().getMaHocKy();
            if (maHocKy != null && maHocKy.length() > 0) {
                try {
                    builder.hocKy(Integer.parseInt(maHocKy.substring(maHocKy.length() - 1)));
                } catch (NumberFormatException e) {
                    builder.hocKy(null);
                }
            }
        }
        
        builder.ngayNop(thanhToan.getNgayThanhToan());
        builder.soPhieu(thanhToan.getMaThanhToan());
        builder.soTienDaNop(thanhToan.getSoTien());
        builder.hinhThuc(thanhToan.getPhuongThuc());
        
        return builder.build();
    }
    
    private ChiTietThanhToanResponse convertToChiTiet(ChiTietHocPhi chiTiet) {
        ChiTietThanhToanResponse.ChiTietThanhToanResponseBuilder builder = ChiTietThanhToanResponse.builder();
        
        if (chiTiet.getHocPhi() != null && chiTiet.getHocPhi().getHocKy() != null) {
            builder.namHoc(chiTiet.getHocPhi().getHocKy().getNamHoc());
            String maHocKy = chiTiet.getHocPhi().getHocKy().getMaHocKy();
            if (maHocKy != null && maHocKy.length() > 0) {
                try {
                    builder.hocKy(Integer.parseInt(maHocKy.substring(maHocKy.length() - 1)));
                } catch (NumberFormatException e) {
                    builder.hocKy(null);
                }
            }
        }
        
        if (chiTiet.getLopHocPhan() != null) {
            builder.maLopHP(chiTiet.getLopHocPhan().getMaLopHP());
            if (chiTiet.getLopHocPhan().getMonHoc() != null) {
                builder.tenHocPhan(chiTiet.getLopHocPhan().getMonHoc().getTenMon());
            }
        }
        
        builder.soTinChi(chiTiet.getSoTinChi());
        builder.soTienNop(chiTiet.getDonGia());
        builder.mienGiam(chiTiet.getMienGiam());
        
        // Tính soTienThucNop = soTinChi × donGia
        if (chiTiet.getSoTinChi() != null && chiTiet.getDonGia() != null) {
            BigDecimal soTienThucNop = chiTiet.getDonGia()
                    .multiply(BigDecimal.valueOf(chiTiet.getSoTinChi()));
            builder.soTienThucNop(soTienThucNop);
        }
        
        return builder.build();
    }
    
    @Transactional
    public XacNhanThanhToanResponse xacNhanThanhToan(XacNhanThanhToanRequest request) {
        // Tìm học phí theo mã sinh viên, học kỳ và năm học
        HocPhi hocPhi = hocPhiRepository.findByMaSVAndHocKyAndNamHoc(
                request.getMaSV(), 
                request.getMaHocKy(), 
                request.getNamHoc()
        );
        
        if (hocPhi == null) {
            throw new RuntimeException("Không tìm thấy thông tin học phí cho học kỳ này");
        }
        
        // Kiểm tra nếu đã thanh toán rồi
        if ("Đã nộp".equalsIgnoreCase(hocPhi.getTrangThai())) {
            throw new RuntimeException("Học phí học kỳ này đã được thanh toán");
        }
        
        // Kiểm tra số tiền thanh toán
        if (request.getSoTien() == null || request.getSoTien().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Số tiền thanh toán không hợp lệ");
        }
        
        // Tạo mã thanh toán
        String maThanhToan = request.getMaGiaoDich() != null && !request.getMaGiaoDich().isEmpty()
                ? request.getMaGiaoDich()
                : "TT" + System.currentTimeMillis();
        
        // Tạo bản ghi thanh toán
        ThanhToan thanhToan = new ThanhToan();
        thanhToan.setMaThanhToan(maThanhToan);
        thanhToan.setSoTien(request.getSoTien());
        thanhToan.setNgayThanhToan(LocalDateTime.now());
        thanhToan.setPhuongThuc(request.getPhuongThuc() != null ? request.getPhuongThuc() : "Chuyển khoản");
        thanhToan.setHocPhi(hocPhi);
        
        // Lưu thanh toán
        thanhToanRepository.save(thanhToan);
        
        // Cập nhật trạng thái học phí thành "Đã nộp"
        hocPhi.setTrangThai("Đã nộp");
        hocPhiRepository.save(hocPhi);
        
        // Tạo response
        return XacNhanThanhToanResponse.builder()
                .maThanhToan(maThanhToan)
                .maSV(request.getMaSV())
                .maHocKy(request.getMaHocKy())
                .namHoc(request.getNamHoc())
                .soTien(request.getSoTien())
                .phuongThuc(thanhToan.getPhuongThuc())
                .ngayThanhToan(thanhToan.getNgayThanhToan())
                .trangThaiHocPhi("Đã nộp")
                .message("Thanh toán học phí thành công")
                .build();
    }
}
