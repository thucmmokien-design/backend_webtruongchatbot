package com.example.chatbotai.service;

import com.example.chatbotai.Entity.SinhVien;
import com.example.chatbotai.Entity.TaiKhoan;
import com.example.chatbotai.dto.requests.UpdateSinhVienRequest;
import com.example.chatbotai.dto.response.SinhVienResponse;
import com.example.chatbotai.exception.AppExcepsion;
import com.example.chatbotai.exception.ErrorCode;
import com.example.chatbotai.repository.SinhVienRepository;
import com.example.chatbotai.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SinhVienService {
    SinhVienRepository sinhVienRepository;
    UserRepository userRepository;

    public SinhVienResponse getSinhVienByUsername(String username) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        return convertToResponse(taiKhoan.getSinhVien());
    }

    @Transactional
    public SinhVienResponse updateSinhVienByUsername(String username, UpdateSinhVienRequest request) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        SinhVien sinhVien = taiKhoan.getSinhVien();
        
        if (request.getHoTen() != null) {
            sinhVien.setHoTen(request.getHoTen());
        }
        if (request.getNgaySinh() != null) {
            sinhVien.setNgaySinh(request.getNgaySinh());
        }
        if (request.getGioiTinh() != null) {
            sinhVien.setGioiTinh(request.getGioiTinh());
        }
        if (request.getEmail() != null) {
            sinhVien.setEmail(request.getEmail());
        }
        if (request.getDienThoai() != null) {
            sinhVien.setDienThoai(request.getDienThoai());
        }
        
        sinhVienRepository.save(sinhVien);
        
        return convertToResponse(sinhVien);
    }

    private SinhVienResponse convertToResponse(SinhVien sinhVien) {
        return SinhVienResponse.builder()
                .maSV(sinhVien.getMaSV())
                .hoTen(sinhVien.getHoTen())
                .ngaySinh(sinhVien.getNgaySinh())
                .gioiTinh(sinhVien.getGioiTinh())
                .email(sinhVien.getEmail())
                .dienThoai(sinhVien.getDienThoai())
                .trangThai(sinhVien.getTrangThai())
                .tenLop(sinhVien.getLop() != null ? sinhVien.getLop().getTenLop() : null)
                .maLop(sinhVien.getLop() != null ? sinhVien.getLop().getMaLop() : null)
                .build();
    }
}
