package com.example.chatbotai.service;

import com.example.chatbotai.Entity.SinhVien;
import com.example.chatbotai.Entity.ThongBao;
import com.example.chatbotai.dto.response.ThongBaoResponse;
import com.example.chatbotai.repository.SinhVienRepository;
import com.example.chatbotai.repository.ThongBaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThongBaoService {
    
    private final ThongBaoRepository thongBaoRepository;
    private final SinhVienRepository sinhVienRepository;
    
    // Lấy tất cả thông báo
    public List<ThongBaoResponse> getAllThongBao() {
        List<ThongBao> thongBaoList = thongBaoRepository.findAllOrderByNgayDangDesc();
        return thongBaoList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Lấy thông báo theo sinh viên
    public List<ThongBaoResponse> getThongBaoByMaSV(String maSV) {
        SinhVien sinhVien = sinhVienRepository.findById(maSV)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
        
        String maKhoa = null;
        String maLop = null;
        
        if (sinhVien.getLop() != null) {
            maLop = sinhVien.getLop().getMaLop();
            if (sinhVien.getLop().getNganh() != null && sinhVien.getLop().getNganh().getKhoa() != null) {
                maKhoa = sinhVien.getLop().getNganh().getKhoa().getMaKhoa();
            }
        }
        
        List<ThongBao> thongBaoList = thongBaoRepository.findByMaKhoaAndMaLop(maKhoa, maLop);
        return thongBaoList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Lấy thông báo theo tag
    public List<ThongBaoResponse> getThongBaoByTag(String tag) {
        List<ThongBao> thongBaoList = thongBaoRepository.findByTag(tag);
        return thongBaoList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Lấy chi tiết thông báo
    public ThongBaoResponse getThongBaoById(String maThongBao) {
        ThongBao thongBao = thongBaoRepository.findById(maThongBao)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        return convertToResponse(thongBao);
    }
    
    private ThongBaoResponse convertToResponse(ThongBao thongBao) {
        ThongBaoResponse.ThongBaoResponseBuilder builder = ThongBaoResponse.builder();
        
        builder.maThongBao(thongBao.getMaThongBao());
        builder.tieuDe(thongBao.getTieuDe());
        builder.noiDung(thongBao.getNoiDung());
        builder.ngayDang(thongBao.getNgayDang());
        builder.phamVi(thongBao.getPhamVi());
        builder.tag(thongBao.getTag());
        builder.link(thongBao.getLink());
        
        if (thongBao.getKhoa() != null) {
            builder.tenKhoa(thongBao.getKhoa().getTenKhoa());
        }
        
        if (thongBao.getLop() != null) {
            builder.tenLop(thongBao.getLop().getTenLop());
        }
        
        return builder.build();
    }
}
