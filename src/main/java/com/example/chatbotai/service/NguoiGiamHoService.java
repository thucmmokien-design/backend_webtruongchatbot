package com.example.chatbotai.service;

import com.example.chatbotai.Entity.TaiKhoan;
import com.example.chatbotai.Entity.ThongTinNguoiGiamHo;
import com.example.chatbotai.dto.requests.UpdateNguoiGiamHoRequest;
import com.example.chatbotai.dto.response.NguoiGiamHoResponse;
import com.example.chatbotai.exception.AppExcepsion;
import com.example.chatbotai.exception.ErrorCode;
import com.example.chatbotai.repository.ThongTinNguoiGiamHoRepository;
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
public class NguoiGiamHoService {
    ThongTinNguoiGiamHoRepository nguoiGiamHoRepository;
    UserRepository userRepository;

    public List<NguoiGiamHoResponse> getNguoiGiamHoByUsername(String username) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        String maSV = taiKhoan.getSinhVien().getMaSV();
        return nguoiGiamHoRepository.findByMaSV(maSV).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public NguoiGiamHoResponse updateNguoiGiamHo(String username, UpdateNguoiGiamHoRequest request) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        String maSV = taiKhoan.getSinhVien().getMaSV();
        
        List<ThongTinNguoiGiamHo> danhSach = nguoiGiamHoRepository.findByMaSV(maSV);
        ThongTinNguoiGiamHo nguoiGiamHo;
        
        if (danhSach.isEmpty()) {
            nguoiGiamHo = new ThongTinNguoiGiamHo();
            nguoiGiamHo.setSinhVien(taiKhoan.getSinhVien());
        } else {
            nguoiGiamHo = danhSach.get(0);
        }
        
        if (request.getHoTen() != null) nguoiGiamHo.setHoTen(request.getHoTen());
        if (request.getNgaySinh() != null) nguoiGiamHo.setNgaySinh(request.getNgaySinh());
        if (request.getDienThoai() != null) nguoiGiamHo.setDienThoai(request.getDienThoai());
        if (request.getSoCCCD() != null) nguoiGiamHo.setSoCCCD(request.getSoCCCD());
        if (request.getHoKhauThuongTru() != null) nguoiGiamHo.setHoKhauThuongTru(request.getHoKhauThuongTru());
        if (request.getMoiQuanHe() != null) nguoiGiamHo.setMoiQuanHe(request.getMoiQuanHe());
        
        nguoiGiamHoRepository.save(nguoiGiamHo);
        
        return convertToResponse(nguoiGiamHo);
    }

    private NguoiGiamHoResponse convertToResponse(ThongTinNguoiGiamHo nguoiGiamHo) {
        return NguoiGiamHoResponse.builder()
                .id(nguoiGiamHo.getId())
                .hoTen(nguoiGiamHo.getHoTen())
                .ngaySinh(nguoiGiamHo.getNgaySinh())
                .dienThoai(nguoiGiamHo.getDienThoai())
                .soCCCD(nguoiGiamHo.getSoCCCD())
                .hoKhauThuongTru(nguoiGiamHo.getHoKhauThuongTru())
                .moiQuanHe(nguoiGiamHo.getMoiQuanHe())
                .build();
    }
}
