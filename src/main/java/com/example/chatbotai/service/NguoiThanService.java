package com.example.chatbotai.service;

import com.example.chatbotai.Entity.TaiKhoan;
import com.example.chatbotai.Entity.ThongTinNguoiThan;
import com.example.chatbotai.dto.requests.UpdateNguoiThanRequest;
import com.example.chatbotai.dto.response.NguoiThanResponse;
import com.example.chatbotai.exception.AppExcepsion;
import com.example.chatbotai.exception.ErrorCode;
import com.example.chatbotai.repository.ThongTinNguoiThanRepository;
import com.example.chatbotai.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NguoiThanService {
    ThongTinNguoiThanRepository nguoiThanRepository;
    UserRepository userRepository;

    public List<NguoiThanResponse> getNguoiThanByUsername(String username) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        String maSV = taiKhoan.getSinhVien().getMaSV();
        return nguoiThanRepository.findByMaSV(maSV).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public NguoiThanResponse updateNguoiThan(String username, UpdateNguoiThanRequest request) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        String maSV = taiKhoan.getSinhVien().getMaSV();
        
        List<ThongTinNguoiThan> danhSach = nguoiThanRepository.findByMaSV(maSV);
        ThongTinNguoiThan nguoiThan;
        
        if (danhSach.isEmpty()) {
            nguoiThan = new ThongTinNguoiThan();
            nguoiThan.setSinhVien(taiKhoan.getSinhVien());
        } else {
            nguoiThan = danhSach.get(0);
        }
        
        if (request.getHoTen() != null) nguoiThan.setHoTen(request.getHoTen());
        if (request.getNgaySinh() != null) nguoiThan.setNgaySinh(request.getNgaySinh());
        if (request.getDienThoai() != null) nguoiThan.setDienThoai(request.getDienThoai());
        if (request.getSoCCCD() != null) nguoiThan.setSoCCCD(request.getSoCCCD());
        if (request.getHoKhauThuongTru() != null) nguoiThan.setHoKhauThuongTru(request.getHoKhauThuongTru());
        if (request.getMoiQuanHe() != null) nguoiThan.setMoiQuanHe(request.getMoiQuanHe());
        
        nguoiThanRepository.save(nguoiThan);
        
        return convertToResponse(nguoiThan);
    }

    private NguoiThanResponse convertToResponse(ThongTinNguoiThan nguoiThan) {
        return NguoiThanResponse.builder()
                .id(nguoiThan.getId())
                .hoTen(nguoiThan.getHoTen())
                .ngaySinh(nguoiThan.getNgaySinh())
                .dienThoai(nguoiThan.getDienThoai())
                .soCCCD(nguoiThan.getSoCCCD())
                .hoKhauThuongTru(nguoiThan.getHoKhauThuongTru())
                .moiQuanHe(nguoiThan.getMoiQuanHe())
                .build();
    }
}
