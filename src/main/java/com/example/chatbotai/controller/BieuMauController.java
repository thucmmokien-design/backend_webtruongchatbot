package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.response.BieuMauResponse;
import com.example.chatbotai.service.BieuMauService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bieu-mau")
@RequiredArgsConstructor
public class BieuMauController {
    
    private final BieuMauService bieuMauService;
    
    /**
     * Lấy tất cả biểu mẫu
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<BieuMauResponse>>> getAllBieuMau() {
        List<BieuMauResponse> danhSach = bieuMauService.getAllBieuMau();
        return ResponseEntity.ok(ApiResponse.<List<BieuMauResponse>>builder()
                .Code(200)
                .Message("Lấy danh sách biểu mẫu thành công")
                .result(danhSach)
                .build());
    }
    
    /**
     * Lấy biểu mẫu theo loại
     */
    @GetMapping("/loai/{loai}")
    public ResponseEntity<ApiResponse<List<BieuMauResponse>>> getBieuMauByLoai(
            @PathVariable String loai) {
        List<BieuMauResponse> danhSach = bieuMauService.getBieuMauByLoai(loai);
        return ResponseEntity.ok(ApiResponse.<List<BieuMauResponse>>builder()
                .Code(200)
                .Message("Lấy danh sách biểu mẫu theo loại thành công")
                .result(danhSach)
                .build());
    }
    
    /**
     * Lấy chi tiết biểu mẫu theo mã
     */
    @GetMapping("/{maBieuMau}")
    public ResponseEntity<ApiResponse<BieuMauResponse>> getBieuMauById(
            @PathVariable String maBieuMau) {
        try {
            BieuMauResponse bieuMau = bieuMauService.getBieuMauById(maBieuMau);
            return ResponseEntity.ok(ApiResponse.<BieuMauResponse>builder()
                    .Code(200)
                    .Message("Lấy thông tin biểu mẫu thành công")
                    .result(bieuMau)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<BieuMauResponse>builder()
                    .Code(400)
                    .Message(e.getMessage())
                    .result(null)
                    .build());
        }
    }
}
