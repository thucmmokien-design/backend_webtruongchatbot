package com.example.chatbotai.service;

import com.example.chatbotai.Entity.ChiTietHocPhi;
import com.example.chatbotai.Entity.HocPhi;
import com.example.chatbotai.Entity.ThanhToan;
import com.example.chatbotai.dto.response.*;
import com.example.chatbotai.repository.ChiTietHocPhiRepository;
import com.example.chatbotai.repository.HocPhiRepository;
import com.example.chatbotai.repository.ThanhToanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HocPhiService {
    
    private final HocPhiRepository hocPhiRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final ChiTietHocPhiRepository chiTietHocPhiRepository;
    
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
}
