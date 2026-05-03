package com.example.chatbotai.service;

import com.example.chatbotai.Entity.BangDiem;
import com.example.chatbotai.dto.response.BangDiemResponse;
import com.example.chatbotai.repository.BangDiemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BangDiemService {
    
    private final BangDiemRepository bangDiemRepository;
    
    public List<BangDiemResponse> getBangDiemByMaSV(String maSV) {
        List<BangDiem> bangDiemList = bangDiemRepository.findByMaSV(maSV);
        
        AtomicInteger stt = new AtomicInteger(1);
        return bangDiemList.stream()
                .map(bd -> convertToResponse(bd, stt.getAndIncrement()))
                .collect(Collectors.toList());
    }
    
    public List<BangDiemResponse> getBangDiemByMaSVAndHocKy(String maSV, String maHocKy, String namHoc) {
        List<BangDiem> bangDiemList = bangDiemRepository.findByMaSVAndHocKy(maSV, maHocKy, namHoc);
        
        AtomicInteger stt = new AtomicInteger(1);
        return bangDiemList.stream()
                .map(bd -> convertToResponse(bd, stt.getAndIncrement()))
                .collect(Collectors.toList());
    }
    
    private BangDiemResponse convertToResponse(BangDiem bangDiem, int stt) {
        BangDiemResponse.BangDiemResponseBuilder builder = BangDiemResponse.builder();
        builder.stt(stt);
        
        // Lấy thông tin từ LopHocPhan
        if (bangDiem.getLopHocPhan() != null) {
            builder.maLopHP(bangDiem.getLopHocPhan().getMaLopHP());
            
            // Lấy thông tin từ MonHoc
            if (bangDiem.getLopHocPhan().getMonHoc() != null) {
                builder.maMon(bangDiem.getLopHocPhan().getMonHoc().getMaMon());
                builder.tenMon(bangDiem.getLopHocPhan().getMonHoc().getTenMon());
                builder.soTinChi(bangDiem.getLopHocPhan().getMonHoc().getSoTinChi());
                builder.loaiMon(bangDiem.getLopHocPhan().getMonHoc().getLoaiMon());
            }
            
            // Lấy thông tin từ HocKy
            if (bangDiem.getLopHocPhan().getHocKy() != null) {
                builder.hocKy(bangDiem.getLopHocPhan().getHocKy().getTenHocKy());
                builder.namHoc(bangDiem.getLopHocPhan().getHocKy().getNamHoc());
            }
        }
        
        // Lấy thông tin điểm
        builder.diemQT(bangDiem.getDiemQT());
        builder.diemThi(bangDiem.getDiemThi());
        
        // Tính điểm tổng = (DiemQT + DiemThi) / 2
        Double diemTong = null;
        if (bangDiem.getDiemQT() != null && bangDiem.getDiemThi() != null) {
            diemTong = (bangDiem.getDiemQT() + bangDiem.getDiemThi()) / 2;
            builder.diemTong(Math.round(diemTong * 100.0) / 100.0);
            
            // Công thức: DiemHe4 = DiemTong * 4 / 10
            double diemHe4 = diemTong * 4 / 10;
            builder.diemHe4(Math.round(diemHe4 * 100.0) / 100.0);
            
            // Công thức: DiemHe10 = DiemTong (đã là hệ số 10)
            builder.diemHe10(Math.round(diemTong * 100.0) / 100.0);
            
            // Xếp loại dựa trên điểm hệ số 10
            builder.xepLoai(getXepLoai(diemTong));
        }
        
        return builder.build();
    }
    
    private String getXepLoai(Double diem) {
        if (diem == null) return null;
        if (diem >= 8.5) return "A";
        if (diem >= 7.0) return "B";
        if (diem >= 5.5) return "C";
        if (diem >= 4.0) return "D";
        return "F";
    }
}
