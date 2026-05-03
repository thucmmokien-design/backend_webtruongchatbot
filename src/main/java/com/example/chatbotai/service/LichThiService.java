package com.example.chatbotai.service;

import com.example.chatbotai.Entity.LichThi;
import com.example.chatbotai.dto.response.LichThiResponse;
import com.example.chatbotai.repository.LichThiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichThiService {
    
    private final LichThiRepository lichThiRepository;
    
    public List<LichThiResponse> getLichThiByMaSinhVien(String maSV) {
        List<LichThi> lichThiList = lichThiRepository.findByMaSinhVien(maSV);
        
        AtomicInteger stt = new AtomicInteger(1);
        return lichThiList.stream()
                .map(lichThi -> convertToResponse(lichThi, stt.getAndIncrement()))
                .collect(Collectors.toList());
    }
    
    public List<LichThiResponse> getLichThiByMaSinhVienAndHocKy(String maSV, String maHocKy, String namHoc) {
        List<LichThi> lichThiList = lichThiRepository.findByMaSinhVienAndHocKy(maSV, maHocKy, namHoc);
        
        AtomicInteger stt = new AtomicInteger(1);
        return lichThiList.stream()
                .map(lichThi -> convertToResponse(lichThi, stt.getAndIncrement()))
                .collect(Collectors.toList());
    }
    
    private LichThiResponse convertToResponse(LichThi lichThi, int stt) {
        LichThiResponse response = new LichThiResponse();
        response.setStt(stt);
        response.setNgayThi(lichThi.getNgayThi());
        response.setPhongThi(lichThi.getPhongThi());
        // Format giờ thi: "HH:mm - HH:mm"
        if (lichThi.getGioBatDau() != null && lichThi.getGioKetThuc() != null) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String gioThi = lichThi.getGioBatDau().format(timeFormatter) + 
                           " - " + 
                           lichThi.getGioKetThuc().format(timeFormatter);
            response.setGioThi(gioThi);
        }
        if (lichThi.getLopHocPhan() != null) {
            response.setMaLopHP(lichThi.getLopHocPhan().getMaLopHP());
            if (lichThi.getLopHocPhan().getMonHoc() != null) {
                response.setTenMonHoc(lichThi.getLopHocPhan().getMonHoc().getTenMon());
                response.setSoTinChi(lichThi.getLopHocPhan().getMonHoc().getSoTinChi());
            }
            // Thông tin học kỳ từ LopHocPhan
            if (lichThi.getLopHocPhan().getHocKy() != null) {
                response.setMaHocKy(lichThi.getLopHocPhan().getHocKy().getMaHocKy());
                response.setTenHocKy(lichThi.getLopHocPhan().getHocKy().getTenHocKy());
                response.setNamHoc(lichThi.getLopHocPhan().getHocKy().getNamHoc());
            }
        }
        response.setHinhThuc("Thi viết");
        return response;
    }
}
