package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.response.LichHocResponse;
import com.example.chatbotai.service.LichHocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichhoc")
@RequiredArgsConstructor
public class LichHocController {
    
    private final LichHocService lichHocService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<LichHocResponse>>> getAllLichHoc() {
        List<LichHocResponse> lichHocList = lichHocService.getAllLichHoc();
        return ResponseEntity.ok(ApiResponse.<List<LichHocResponse>>builder()
                .Code(200)
                .Message("Lấy danh sách lịch học thành công")
                .result(lichHocList)
                .build());
    }
    
    @GetMapping("/{maLichHoc}")
    public ResponseEntity<ApiResponse<LichHocResponse>> getLichHocById(@PathVariable String maLichHoc) {
        LichHocResponse lichHoc = lichHocService.getLichHocById(maLichHoc);
        return ResponseEntity.ok(ApiResponse.<LichHocResponse>builder()
                .Code(200)
                .Message("Lấy thông tin lịch học thành công")
                .result(lichHoc)
                .build());
    }
    
    @GetMapping("/lophocphan/{maLopHP}")
    public ResponseEntity<ApiResponse<List<LichHocResponse>>> getLichHocByLopHP(@PathVariable String maLopHP) {
        List<LichHocResponse> lichHocList = lichHocService.getLichHocByLopHP(maLopHP);
        return ResponseEntity.ok(ApiResponse.<List<LichHocResponse>>builder()
                .Code(200)
                .Message("Lấy lịch học theo lớp học phần thành công")
                .result(lichHocList)
                .build());
    }
    
    @GetMapping("/thu/{thu}")
    public ResponseEntity<ApiResponse<List<LichHocResponse>>> getLichHocByThu(@PathVariable Integer thu) {
        List<LichHocResponse> lichHocList = lichHocService.getLichHocByThu(thu);
        return ResponseEntity.ok(ApiResponse.<List<LichHocResponse>>builder()
                .Code(200)
                .Message("Lấy lịch học theo thứ thành công")
                .result(lichHocList)
                .build());
    }
    
    @GetMapping("/sinhvien/{maSV}")
    public ResponseEntity<ApiResponse<List<LichHocResponse>>> getLichHocByMaSV(@PathVariable String maSV) {
        List<LichHocResponse> lichHocList = lichHocService.getLichHocByMaSV(maSV);
        return ResponseEntity.ok(ApiResponse.<List<LichHocResponse>>builder()
                .Code(200)
                .Message("Lấy lịch học của sinh viên thành công")
                .result(lichHocList)
                .build());
    }
    
    @GetMapping("/sinhvien/{maSV}/thu/{thu}")
    public ResponseEntity<ApiResponse<List<LichHocResponse>>> getLichHocByMaSVAndThu(
            @PathVariable String maSV, 
            @PathVariable Integer thu) {
        List<LichHocResponse> lichHocList = lichHocService.getLichHocByMaSVAndThu(maSV, thu);
        return ResponseEntity.ok(ApiResponse.<List<LichHocResponse>>builder()
                .Code(200)
                .Message("Lấy lịch học của sinh viên theo thứ thành công")
                .result(lichHocList)
                .build());
    }
    
    @GetMapping("/sinhvien/{maSV}/tuan")
    public ResponseEntity<ApiResponse<List<LichHocResponse>>> getLichHocByMaSVAndWeek(
            @PathVariable String maSV,
            @RequestParam String ngayBatDau,
            @RequestParam String ngayKetThuc) {
        java.time.LocalDate startDate = java.time.LocalDate.parse(ngayBatDau);
        java.time.LocalDate endDate = java.time.LocalDate.parse(ngayKetThuc);
        
        List<LichHocResponse> lichHocList = lichHocService.getLichHocByMaSVAndDateRange(maSV, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.<List<LichHocResponse>>builder()
                .Code(200)
                .Message("Lấy lịch học của sinh viên theo tuần thành công")
                .result(lichHocList)
                .build());
    }
    
    @GetMapping("/sinhvien/{maSV}/hoc-ky")
    public ResponseEntity<ApiResponse<List<LichHocResponse>>> getLichHocByMaSVAndHocKy(
            @PathVariable String maSV,
            @RequestParam String maHocKy,
            @RequestParam(required = false) String namHoc,
            @RequestParam(required = false) Integer nhipHoc) {
        List<LichHocResponse> lichHocList = lichHocService.getLichHocByMaSVAndHocKy(maSV, maHocKy, namHoc, nhipHoc);
        return ResponseEntity.ok(ApiResponse.<List<LichHocResponse>>builder()
                .Code(200)
                .Message("Lấy lịch học của sinh viên theo học kỳ thành công")
                .result(lichHocList)
                .build());
    }
}
