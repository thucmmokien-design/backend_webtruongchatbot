package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.response.LichThiResponse;
import com.example.chatbotai.service.LichThiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lich-thi")
@RequiredArgsConstructor
public class LichThiController {
    
    private final LichThiService lichThiService;
    
    @GetMapping("/{maSV}")
    public ApiResponse<List<LichThiResponse>> getLichThiByMaSinhVien(@PathVariable String maSV) {
        List<LichThiResponse> lichThiList = lichThiService.getLichThiByMaSinhVien(maSV);
        return ApiResponse.<List<LichThiResponse>>builder()
                .result(lichThiList)
                .build();
    }
    
    @GetMapping("/{maSV}/hoc-ky")
    public ApiResponse<List<LichThiResponse>> getLichThiByMaSinhVienAndHocKy(
            @PathVariable String maSV,
            @RequestParam String maHocKy,
            @RequestParam(required = false) String namHoc) {
        List<LichThiResponse> lichThiList = lichThiService.getLichThiByMaSinhVienAndHocKy(maSV, maHocKy, namHoc);
        return ApiResponse.<List<LichThiResponse>>builder()
                .result(lichThiList)
                .build();
    }
}
