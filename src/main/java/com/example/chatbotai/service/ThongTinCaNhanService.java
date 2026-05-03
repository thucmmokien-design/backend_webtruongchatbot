package com.example.chatbotai.service;

import com.example.chatbotai.Entity.SinhVien;
import com.example.chatbotai.Entity.TaiKhoan;
import com.example.chatbotai.Entity.ThongTinCaNhan;
import com.example.chatbotai.dto.requests.UpdateThongTinCaNhanRequest;
import com.example.chatbotai.dto.response.ThongTinCaNhanResponse;
import com.example.chatbotai.exception.AppExcepsion;
import com.example.chatbotai.exception.ErrorCode;
import com.example.chatbotai.repository.ThongTinCaNhanRepository;
import com.example.chatbotai.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongTinCaNhanService {
    ThongTinCaNhanRepository thongTinCaNhanRepository;
    UserRepository userRepository;

    public ThongTinCaNhanResponse getThongTinCaNhanByUsername(String username) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        String maSV = taiKhoan.getSinhVien().getMaSV();
        ThongTinCaNhan thongTin = thongTinCaNhanRepository.findByMaSV(maSV).orElse(null);
        
        if (thongTin == null) {
            return ThongTinCaNhanResponse.builder().maSV(maSV).build();
        }
        
        return convertToResponse(thongTin);
    }

    @Transactional
    public ThongTinCaNhanResponse updateThongTinCaNhan(String username, UpdateThongTinCaNhanRequest request) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        String maSV = taiKhoan.getSinhVien().getMaSV();
        ThongTinCaNhan thongTin = thongTinCaNhanRepository.findByMaSV(maSV)
                .orElseGet(() -> {
                    ThongTinCaNhan newThongTin = new ThongTinCaNhan();
                    newThongTin.setMaSV(maSV);
                    newThongTin.setSinhVien(taiKhoan.getSinhVien());
                    return newThongTin;
                });
        
        if (request.getEmail() != null) thongTin.setEmail(request.getEmail());
        if (request.getDienThoai() != null) thongTin.setDienThoai(request.getDienThoai());
        if (request.getSoTaiKhoan() != null) thongTin.setSoTaiKhoan(request.getSoTaiKhoan());
        if (request.getTenNganHang() != null) thongTin.setTenNganHang(request.getTenNganHang());
        if (request.getSoCCCD() != null) thongTin.setSoCCCD(request.getSoCCCD());
        if (request.getSoBaoHiem() != null) thongTin.setSoBaoHiem(request.getSoBaoHiem());
        if (request.getDanToc() != null) thongTin.setDanToc(request.getDanToc());
        if (request.getTonGiao() != null) thongTin.setTonGiao(request.getTonGiao());
        if (request.getQueQuan() != null) thongTin.setQueQuan(request.getQueQuan());
        if (request.getXa() != null) thongTin.setXa(request.getXa());
        if (request.getTinhTP() != null) thongTin.setTinhTP(request.getTinhTP());
        if (request.getQuocTich() != null) thongTin.setQuocTich(request.getQuocTich());
        if (request.getHoKhauThuongTru() != null) thongTin.setHoKhauThuongTru(request.getHoKhauThuongTru());
        if (request.getNoiO() != null) thongTin.setNoiO(request.getNoiO());
        
        thongTinCaNhanRepository.save(thongTin);
        
        return convertToResponse(thongTin);
    }

    private ThongTinCaNhanResponse convertToResponse(ThongTinCaNhan thongTin) {
        return ThongTinCaNhanResponse.builder()
                .maSV(thongTin.getMaSV())
                .email(thongTin.getEmail())
                .dienThoai(thongTin.getDienThoai())
                .soTaiKhoan(thongTin.getSoTaiKhoan())
                .tenNganHang(thongTin.getTenNganHang())
                .soCCCD(thongTin.getSoCCCD())
                .soBaoHiem(thongTin.getSoBaoHiem())
                .danToc(thongTin.getDanToc())
                .tonGiao(thongTin.getTonGiao())
                .queQuan(thongTin.getQueQuan())
                .xa(thongTin.getXa())
                .tinhTP(thongTin.getTinhTP())
                .quocTich(thongTin.getQuocTich())
                .hoKhauThuongTru(thongTin.getHoKhauThuongTru())
                .noiO(thongTin.getNoiO())
                .build();
    }
}
