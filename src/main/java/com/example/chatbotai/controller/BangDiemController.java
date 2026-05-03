package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.response.BangDiemResponse;
import com.example.chatbotai.service.BangDiemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bang-diem")
@RequiredArgsConstructor
public class BangDiemController {
    
    private final BangDiemService bangDiemService;
    
    @GetMapping("/{maSV}")
    public ResponseEntity<ApiResponse<List<BangDiemResponse>>> getBangDiemByMaSV(@PathVariable String maSV) {
        List<BangDiemResponse> bangDiemList = bangDiemService.getBangDiemByMaSV(maSV);
        return ResponseEntity.ok(ApiResponse.<List<BangDiemResponse>>builder()
                .Code(200)
                .Message("Lấy bảng điểm của sinh viên thành công")
                .result(bangDiemList)
                .build());
    }
    
    @GetMapping("/{maSV}/hoc-ky")
    public ResponseEntity<ApiResponse<List<BangDiemResponse>>> getBangDiemByMaSVAndHocKy(
            @PathVariable String maSV,
            @RequestParam String maHocKy,
            @RequestParam(required = false) String namHoc) {
        List<BangDiemResponse> bangDiemList = bangDiemService.getBangDiemByMaSVAndHocKy(maSV, maHocKy, namHoc);
        return ResponseEntity.ok(ApiResponse.<List<BangDiemResponse>>builder()
                .Code(200)
                .Message("Lấy bảng điểm của sinh viên theo học kỳ thành công")
                .result(bangDiemList)
                .build());
    }
}
