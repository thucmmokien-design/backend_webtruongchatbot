package com.example.chatbotai.service;

import com.example.chatbotai.Entity.*;
import com.example.chatbotai.dto.requests.DangKyLopHocRequest;
import com.example.chatbotai.dto.response.DangKyLopHocResponse;
import com.example.chatbotai.dto.response.LopHocDuocMoResponse;
import com.example.chatbotai.repository.DangKyChoDuyetRepository;
import com.example.chatbotai.repository.LopHocDuocMoRepository;
import com.example.chatbotai.repository.SinhVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DangKyLopHocService {
    
    private final LopHocDuocMoRepository lopHocDuocMoRepository;
    private final DangKyChoDuyetRepository dangKyChoDuyetRepository;
    private final SinhVienRepository sinhVienRepository;
    
    /**
     * Lấy danh sách tất cả lớp học được mở theo học kỳ
     * Kèm theo trạng thái đăng ký của sinh viên (nếu có)
     */
    public List<LopHocDuocMoResponse> getDanhSachLopHoc(String maHocKy, String namHoc, String maSV) {
        List<LopHocDuocMo> danhSachLop;
        
        // Nếu có maHocKy và namHoc thì lọc theo học kỳ, nếu không thì lấy tất cả
        if (maHocKy != null && namHoc != null) {
            danhSachLop = lopHocDuocMoRepository.findByHocKyAndNamHoc(maHocKy, namHoc);
        } else {
            danhSachLop = lopHocDuocMoRepository.findAll();
        }
        
        // Lấy danh sách đăng ký của sinh viên (nếu có maSV)
        List<DangKyChoDuyet> danhSachDangKy = (maSV != null) 
                ? dangKyChoDuyetRepository.findByMaSV(maSV) 
                : List.of();
        
        return danhSachLop.stream()
                .map(lop -> {
                    LopHocDuocMoResponse response = convertToResponse(lop);
                    
                    // Tìm trạng thái đăng ký của sinh viên cho lớp này
                    if (maSV != null) {
                        Optional<DangKyChoDuyet> dangKy = danhSachDangKy.stream()
                                .filter(dk -> dk.getLopHocDuocMo().getMaMo().equals(lop.getMaMo()))
                                .findFirst();
                        
                        if (dangKy.isPresent()) {
                            response.setTrangThaiDangKy(dangKy.get().getTrangThai());
                            response.setIdDangKy(dangKy.get().getId());
                        }
                    }
                    
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy danh sách lớp học còn chỗ trống
     */
    public List<LopHocDuocMoResponse> getDanhSachLopConCho(String maHocKy, String namHoc, String maSV) {
        List<LopHocDuocMo> danhSachLop;
        
        // Nếu có maHocKy và namHoc thì lọc theo học kỳ, nếu không thì lấy tất cả lớp còn chỗ
        if (maHocKy != null && namHoc != null) {
            danhSachLop = lopHocDuocMoRepository.findAvailableClasses(maHocKy, namHoc);
        } else {
            // Lấy tất cả lớp còn chỗ trống
            danhSachLop = lopHocDuocMoRepository.findAll().stream()
                    .filter(lop -> lop.getSoLuongTrong() != null && lop.getSoLuongTrong() > 0)
                    .collect(Collectors.toList());
        }
        
        // Lấy danh sách đăng ký của sinh viên (nếu có maSV)
        List<DangKyChoDuyet> danhSachDangKy = (maSV != null) 
                ? dangKyChoDuyetRepository.findByMaSV(maSV) 
                : List.of();
        
        return danhSachLop.stream()
                .map(lop -> {
                    LopHocDuocMoResponse response = convertToResponse(lop);
                    
                    // Tìm trạng thái đăng ký của sinh viên cho lớp này
                    if (maSV != null) {
                        Optional<DangKyChoDuyet> dangKy = danhSachDangKy.stream()
                                .filter(dk -> dk.getLopHocDuocMo().getMaMo().equals(lop.getMaMo()))
                                .findFirst();
                        
                        if (dangKy.isPresent()) {
                            response.setTrangThaiDangKy(dangKy.get().getTrangThai());
                            response.setIdDangKy(dangKy.get().getId());
                        }
                    }
                    
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Đăng ký lớp học - tạo bản ghi với trạng thái "Chờ duyệt"
     */
    @Transactional
    public DangKyLopHocResponse dangKyLopHoc(DangKyLopHocRequest request) {
        // Kiểm tra sinh viên tồn tại
        SinhVien sinhVien = sinhVienRepository.findById(request.getMaSV())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với mã: " + request.getMaSV()));
        
        // Kiểm tra lớp học tồn tại
        LopHocDuocMo lopHoc = lopHocDuocMoRepository.findById(request.getMaMo())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học với mã: " + request.getMaMo()));
        
        // Kiểm tra đã đăng ký chưa
        boolean daDangKy = dangKyChoDuyetRepository.existsByMaSVAndMaMo(request.getMaSV(), request.getMaMo());
        if (daDangKy) {
            throw new RuntimeException("Bạn đã đăng ký lớp học này rồi");
        }
        
        // Kiểm tra còn chỗ trống không
        if (lopHoc.getSoLuongTrong() == null || lopHoc.getSoLuongTrong() <= 0) {
            throw new RuntimeException("Lớp học đã hết chỗ");
        }
        
        // Tạo bản ghi đăng ký
        DangKyChoDuyet dangKy = new DangKyChoDuyet();
        dangKy.setSinhVien(sinhVien);
        dangKy.setLopHocDuocMo(lopHoc);
        dangKy.setNgayDangKy(LocalDateTime.now());
        dangKy.setTrangThai("Chờ duyệt");
        
        dangKy = dangKyChoDuyetRepository.save(dangKy);
        
        // Giảm số lượng chỗ trống
        lopHoc.setSoLuongTrong(lopHoc.getSoLuongTrong() - 1);
        lopHocDuocMoRepository.save(lopHoc);
        
        return DangKyLopHocResponse.builder()
                .id(dangKy.getId())
                .maSV(request.getMaSV())
                .maMo(request.getMaMo())
                .tenMon(lopHoc.getMonHoc() != null ? lopHoc.getMonHoc().getTenMon() : null)
                .tenGV(lopHoc.getGiangVien() != null ? lopHoc.getGiangVien().getTenGV() : null)
                .ngayDangKy(dangKy.getNgayDangKy())
                .trangThai("Chờ duyệt")
                .message("Đăng ký lớp học thành công, đang chờ duyệt")
                .build();
    }
    
    /**
     * Hủy đăng ký lớp học - xóa bản ghi đăng ký
     */
    @Transactional
    public DangKyLopHocResponse huyDangKy(Integer idDangKy) {
        // Tìm bản ghi đăng ký
        DangKyChoDuyet dangKy = dangKyChoDuyetRepository.findById(idDangKy)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi đăng ký với ID: " + idDangKy));
        
        // Chỉ cho phép hủy nếu đang ở trạng thái "Chờ duyệt"
        if (!"Chờ duyệt".equals(dangKy.getTrangThai())) {
            throw new RuntimeException("Chỉ có thể hủy đăng ký khi đang ở trạng thái 'Chờ duyệt'");
        }
        
        String maSV = dangKy.getSinhVien().getMaSV();
        String maMo = dangKy.getLopHocDuocMo().getMaMo();
        String tenMon = dangKy.getLopHocDuocMo().getMonHoc() != null 
                ? dangKy.getLopHocDuocMo().getMonHoc().getTenMon() : null;
        
        // Tăng lại số lượng chỗ trống
        LopHocDuocMo lopHoc = dangKy.getLopHocDuocMo();
        lopHoc.setSoLuongTrong(lopHoc.getSoLuongTrong() + 1);
        lopHocDuocMoRepository.save(lopHoc);
        
        // Xóa bản ghi đăng ký
        dangKyChoDuyetRepository.delete(dangKy);
        
        return DangKyLopHocResponse.builder()
                .id(idDangKy)
                .maSV(maSV)
                .maMo(maMo)
                .tenMon(tenMon)
                .trangThai(null)
                .message("Hủy đăng ký thành công")
                .build();
    }
    
    /**
     * Lấy danh sách đăng ký của sinh viên
     */
    public List<DangKyLopHocResponse> getDanhSachDangKy(String maSV) {
        List<DangKyChoDuyet> danhSach = dangKyChoDuyetRepository.findByMaSV(maSV);
        
        return danhSach.stream()
                .map(dk -> DangKyLopHocResponse.builder()
                        .id(dk.getId())
                        .maSV(dk.getSinhVien().getMaSV())
                        .maMo(dk.getLopHocDuocMo().getMaMo())
                        .tenMon(dk.getLopHocDuocMo().getMonHoc() != null 
                                ? dk.getLopHocDuocMo().getMonHoc().getTenMon() : null)
                        .tenGV(dk.getLopHocDuocMo().getGiangVien() != null 
                                ? dk.getLopHocDuocMo().getGiangVien().getTenGV() : null)
                        .ngayDangKy(dk.getNgayDangKy())
                        .trangThai(dk.getTrangThai())
                        .build())
                .collect(Collectors.toList());
    }
    
    private LopHocDuocMoResponse convertToResponse(LopHocDuocMo lop) {
        LopHocDuocMoResponse.LopHocDuocMoResponseBuilder builder = LopHocDuocMoResponse.builder();
        
        builder.maMo(lop.getMaMo());
        builder.ngayBatDau(lop.getNgayBatDau());
        builder.ngayKetThuc(lop.getNgayKetThuc());
        builder.thu(lop.getThu());
        builder.tietBatDau(lop.getTietBatDau());
        builder.tietKetThuc(lop.getTietKetThuc());
        builder.phong(lop.getPhong());
        builder.siSoToiDa(lop.getSiSoToiDa());
        builder.soLuongTrong(lop.getSoLuongTrong());
        
        if (lop.getMonHoc() != null) {
            builder.maMon(lop.getMonHoc().getMaMon());
            builder.tenMon(lop.getMonHoc().getTenMon());
            builder.soTinChi(lop.getMonHoc().getSoTinChi());
        }
        
        if (lop.getGiangVien() != null) {
            builder.maGV(lop.getGiangVien().getMaGV());
            builder.tenGV(lop.getGiangVien().getTenGV());
        }
        
        if (lop.getHocKy() != null) {
            builder.maHocKy(lop.getHocKy().getMaHocKy());
            builder.namHoc(lop.getHocKy().getNamHoc());
        }
        
        if (lop.getNhipHoc() != null) {
            builder.maNhip(lop.getNhipHoc().getMaNhip());
            builder.tenNhip(lop.getNhipHoc().getTenNhip());
        }
        
        return builder.build();
    }
}
