package com.example.chatbotai.service;

import com.example.chatbotai.Entity.Lop;
import com.example.chatbotai.Entity.Nganh;
import com.example.chatbotai.Entity.SinhVien;
import com.example.chatbotai.Entity.TaiKhoan;
import com.example.chatbotai.Entity.ThongTinCaNhan;
import com.example.chatbotai.dto.requests.UpdateSinhVienRequest;
import com.example.chatbotai.dto.response.SinhVienResponse;
import com.example.chatbotai.exception.AppExcepsion;
import com.example.chatbotai.exception.ErrorCode;
import com.example.chatbotai.repository.SinhVienRepository;
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
public class SinhVienService {
    SinhVienRepository sinhVienRepository;
    UserRepository userRepository;
    ThongTinCaNhanRepository thongTinCaNhanRepository;

    public SinhVienResponse getSinhVienByUsername(String username) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        SinhVien sinhVien = taiKhoan.getSinhVien();
        String maSV = sinhVien.getMaSV();
        ThongTinCaNhan thongTin = thongTinCaNhanRepository.findByMaSV(maSV)
                .orElseGet(() -> {
                    ThongTinCaNhan newThongTin = new ThongTinCaNhan();
                    newThongTin.setMaSV(maSV);
                    return newThongTin;
                });
        
        return convertToResponse(sinhVien, thongTin);
    }

    @Transactional
    public SinhVienResponse updateSinhVienByUsername(String username, UpdateSinhVienRequest request) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (taiKhoan.getSinhVien() == null) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        
        SinhVien sinhVien = taiKhoan.getSinhVien();
        String maSV = sinhVien.getMaSV();
        
        // Chỉ cập nhật thông tin trong bảng ThongTinCaNhan
        ThongTinCaNhan thongTin = thongTinCaNhanRepository.findByMaSV(maSV)
                .orElseGet(() -> {
                    ThongTinCaNhan newThongTin = new ThongTinCaNhan();
                    newThongTin.setMaSV(maSV);
                    newThongTin.setSinhVien(sinhVien);
                    return newThongTin;
                });
        
        // Cập nhật tất cả các trường từ request vào ThongTinCaNhan
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
        
        // Lấy lại thông tin đã cập nhật để trả về
        ThongTinCaNhan updatedThongTin = thongTinCaNhanRepository.findByMaSV(maSV).orElse(thongTin);
        
        return convertToResponse(sinhVien, updatedThongTin);
    }

    private SinhVienResponse convertToResponse(SinhVien sinhVien, ThongTinCaNhan thongTin) {
        SinhVienResponse.SinhVienResponseBuilder builder = SinhVienResponse.builder()
                .maSV(sinhVien.getMaSV())
                .hoTen(sinhVien.getHoTen())
                .ngaySinh(sinhVien.getNgaySinh())
                .gioiTinh(sinhVien.getGioiTinh())
                .trangThai(sinhVien.getTrangThai());
        
        // Thông tin từ Lop
        if (sinhVien.getLop() != null) {
            Lop lop = sinhVien.getLop();
            builder.maLop(lop.getMaLop())
                   .tenLop(lop.getTenLop())
                   .khoaHoc(lop.getKhoaHoc());
            
            // Tính niên khóa từ khoaHoc (ví dụ: 2023 -> 2023 - 2028)
            if (lop.getKhoaHoc() != null) {
                try {
                    int namBatDau = Integer.parseInt(lop.getKhoaHoc());
                    int namKetThuc = namBatDau + 5;
                    builder.nienKhoa(namBatDau + " - " + namKetThuc);
                } catch (NumberFormatException e) {
                    builder.nienKhoa(null);
                }
            }
            
            // Thông tin từ Nganh
            if (lop.getNganh() != null) {
                Nganh nganh = lop.getNganh();
                builder.chuyenNganh(nganh.getTenNganh());
                
                // Thông tin từ Khoa (hệ đào tạo)
                if (nganh.getKhoa() != null) {
                    builder.heDaoTao(nganh.getKhoa().getTenKhoa());
                }
            }
        }
        
        // Thông tin chi tiết từ ThongTinCaNhan
        if (thongTin != null) {
            builder.email(thongTin.getEmail())
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
                   .noiO(thongTin.getNoiO());
        }
        
        return builder.build();
    }
}
