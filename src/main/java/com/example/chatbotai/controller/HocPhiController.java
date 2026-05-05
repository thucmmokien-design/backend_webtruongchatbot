package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.requests.XacNhanThanhToanRequest;
import com.example.chatbotai.dto.response.HocPhiResponse;
import com.example.chatbotai.dto.response.ThanhToanHocKyResponse;
import com.example.chatbotai.dto.response.XacNhanThanhToanResponse;
import com.example.chatbotai.service.HocPhiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hoc-phi")
@RequiredArgsConstructor
public class HocPhiController {
    
    private final HocPhiService hocPhiService;
    
    @GetMapping("/{maSV}")
    public ResponseEntity<ApiResponse<HocPhiResponse>> getHocPhiByMaSV(@PathVariable String maSV) {
        HocPhiResponse hocPhiResponse = hocPhiService.getHocPhiByMaSV(maSV);
        return ResponseEntity.ok(ApiResponse.<HocPhiResponse>builder()
                .Code(200)
                .Message("Lấy thông tin học phí thành công")
                .result(hocPhiResponse)
                .build());
    }
    
    @GetMapping("/{maSV}/thanh-toan")
    public ResponseEntity<ApiResponse<ThanhToanHocKyResponse>> getThanhToanByHocKy(
            @PathVariable String maSV,
            @RequestParam String maHocKy,
            @RequestParam String namHoc) {
        ThanhToanHocKyResponse response = hocPhiService.getThanhToanByHocKy(maSV, maHocKy, namHoc);
        return ResponseEntity.ok(ApiResponse.<ThanhToanHocKyResponse>builder()
                .Code(200)
                .Message("Lấy thông tin thanh toán theo học kỳ thành công")
                .result(response)
                .build());
    }
    
    @PostMapping("/xac-nhan-thanh-toan")
    public ResponseEntity<ApiResponse<XacNhanThanhToanResponse>> xacNhanThanhToan(
            @RequestBody XacNhanThanhToanRequest request) {
        try {
            XacNhanThanhToanResponse response = hocPhiService.xacNhanThanhToan(request);
            return ResponseEntity.ok(ApiResponse.<XacNhanThanhToanResponse>builder()
                    .Code(200)
                    .Message("Xác nhận thanh toán thành công")
                    .result(response)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<XacNhanThanhToanResponse>builder()
                    .Code(400)
                    .Message(e.getMessage())
                    .result(null)
                    .build());
        }
    }
}
