package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.response.HocPhiResponse;
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
}
