package com.example.chatbotai.service;

import com.example.chatbotai.Entity.DangKyHoc;
import com.example.chatbotai.Entity.LichHoc;
import com.example.chatbotai.dto.response.LichHocResponse;
import com.example.chatbotai.repository.DangKyHocRepository;
import com.example.chatbotai.repository.LichHocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichHocService {
    
    private final LichHocRepository lichHocRepository;
    private final DangKyHocRepository dangKyHocRepository;
    
    public List<LichHocResponse> getAllLichHoc() {
        return lichHocRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public LichHocResponse getLichHocById(String maLichHoc) {
        LichHoc lichHoc = lichHocRepository.findById(maLichHoc)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch học với mã: " + maLichHoc));
        return convertToResponse(lichHoc);
    }
    
    public List<LichHocResponse> getLichHocByLopHP(String maLopHP) {
        return lichHocRepository.findByMaLopHP(maLopHP).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<LichHocResponse> getLichHocByThu(Integer thu) {
        return lichHocRepository.findByThu(thu).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<LichHocResponse> getLichHocByMaSV(String maSV) {
        // Lấy danh sách đăng ký học của sinh viên
        List<DangKyHoc> dangKyHocList = dangKyHocRepository.findByMaSV(maSV);
        
        if (dangKyHocList.isEmpty()) {
            throw new RuntimeException("Sinh viên chưa đăng ký học phần nào");
        }
        
        // Lấy danh sách mã lớp học phần
        List<String> maLopHPList = dangKyHocList.stream()
                .map(dk -> dk.getLopHocPhan().getMaLopHP())
                .collect(Collectors.toList());
        
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();
        
        // Lấy lịch học theo danh sách mã lớp học phần và lọc theo thời gian
        return lichHocRepository.findByMaLopHPIn(maLopHPList).stream()
                .filter(lh -> lh.getNgayBatDau() != null && lh.getNgayKetThuc() != null)
                .filter(lh -> !currentDate.isBefore(lh.getNgayBatDau()) && !currentDate.isAfter(lh.getNgayKetThuc()))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<LichHocResponse> getLichHocByMaSVAndThu(String maSV, Integer thu) {
        // Lấy danh sách đăng ký học của sinh viên
        List<DangKyHoc> dangKyHocList = dangKyHocRepository.findByMaSV(maSV);
        
        if (dangKyHocList.isEmpty()) {
            throw new RuntimeException("Sinh viên chưa đăng ký học phần nào");
        }
        
        // Lấy danh sách mã lớp học phần
        List<String> maLopHPList = dangKyHocList.stream()
                .map(dk -> dk.getLopHocPhan().getMaLopHP())
                .collect(Collectors.toList());
        
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();
        
        // Lấy lịch học theo danh sách mã lớp học phần, lọc theo thứ và thời gian
        return lichHocRepository.findByMaLopHPIn(maLopHPList).stream()
                .filter(lh -> lh.getThu() != null && lh.getThu().equals(thu))
                .filter(lh -> lh.getNgayBatDau() != null && lh.getNgayKetThuc() != null)
                .filter(lh -> !currentDate.isBefore(lh.getNgayBatDau()) && !currentDate.isAfter(lh.getNgayKetThuc()))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<LichHocResponse> getLichHocByMaSVAndDateRange(String maSV, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        List<LichHoc> lichHocList = lichHocRepository.findByMaSVAndDateRange(maSV, ngayBatDau, ngayKetThuc);
        return lichHocList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<LichHocResponse> getLichHocByMaSVAndHocKy(String maSV, String maHocKy, String namHoc) {
        List<LichHoc> lichHocList = lichHocRepository.findByMaSVAndHocKy(maSV, maHocKy, namHoc);
        return lichHocList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<LichHocResponse> getLichHocByMaSVAndHocKy(String maSV, String maHocKy, String namHoc, Integer nhipHoc) {
        List<LichHoc> lichHocList;
        
        if (nhipHoc != null && nhipHoc > 0) {
            // Tạo mã nhịp từ số nhịp và mã học kỳ (VD: nhipHoc=1, maHocKy=24HK2 -> maNhip=N1_24HK2)
            String maNhip = "N" + nhipHoc + "_" + maHocKy;
            lichHocList = lichHocRepository.findByMaSVAndHocKyAndNhipHoc(maSV, maHocKy, namHoc, maNhip);
        } else {
            // Không lọc theo nhịp học
            lichHocList = lichHocRepository.findByMaSVAndHocKy(maSV, maHocKy, namHoc);
        }
        
        return lichHocList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private LichHocResponse convertToResponse(LichHoc lichHoc) {
        LichHocResponse response = new LichHocResponse();
        response.setMaLichHoc(lichHoc.getMaLichHoc());
        response.setThu(lichHoc.getThu());
        response.setTietBatDau(lichHoc.getTietBatDau());
        response.setTietKetThuc(lichHoc.getTietKetThuc());
        response.setPhong(lichHoc.getPhong());
        response.setNgayBatDau(lichHoc.getNgayBatDau());
        response.setNgayKetThuc(lichHoc.getNgayKetThuc());
        
        if (lichHoc.getLopHocPhan() != null) {
            response.setMaLopHP(lichHoc.getLopHocPhan().getMaLopHP());
            if (lichHoc.getLopHocPhan().getMonHoc() != null) {
                response.setTenMonHoc(lichHoc.getLopHocPhan().getMonHoc().getTenMon());
            }
            if (lichHoc.getLopHocPhan().getGiangVien() != null) {
                response.setTenGiangVien(lichHoc.getLopHocPhan().getGiangVien().getTenGV());
            }
        }
        
        return response;
    }
}
