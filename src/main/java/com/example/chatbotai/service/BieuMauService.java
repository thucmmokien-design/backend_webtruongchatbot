package com.example.chatbotai.service;

import com.example.chatbotai.Entity.BieuMau;
import com.example.chatbotai.dto.response.BieuMauResponse;
import com.example.chatbotai.repository.BieuMauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BieuMauService {
    
    private final BieuMauRepository bieuMauRepository;
    
    /**
     * Lấy tất cả biểu mẫu
     */
    public List<BieuMauResponse> getAllBieuMau() {
        return bieuMauRepository.findAllOrderByNgayTaoDesc().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy biểu mẫu theo loại
     */
    public List<BieuMauResponse> getBieuMauByLoai(String loai) {
        return bieuMauRepository.findByLoaiBieuMau(loai).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy chi tiết biểu mẫu theo mã
     */
    public BieuMauResponse getBieuMauById(String maBieuMau) {
        BieuMau bieuMau = bieuMauRepository.findById(maBieuMau)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy biểu mẫu với mã: " + maBieuMau));
        return convertToResponse(bieuMau);
    }
    
    private BieuMauResponse convertToResponse(BieuMau bieuMau) {
        return BieuMauResponse.builder()
                .maBieuMau(bieuMau.getMaBieuMau())
                .tenBieuMau(bieuMau.getTenBieuMau())
                .moTa(bieuMau.getMoTa())
                .linkDrive(bieuMau.getLinkDrive())
                .loaiBieuMau(bieuMau.getLoaiBieuMau())
                .ngayTao(bieuMau.getNgayTao())
                .build();
    }
}
