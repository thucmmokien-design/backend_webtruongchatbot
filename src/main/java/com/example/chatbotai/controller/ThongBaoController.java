package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.response.ThongBaoResponse;
import com.example.chatbotai.service.ThongBaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thong-bao")
@RequiredArgsConstructor
public class ThongBaoController {
    
    private final ThongBaoService thongBaoService;
    
    // Lấy tất cả thông báo
    @GetMapping
    public ResponseEntity<ApiResponse<List<ThongBaoResponse>>> getAllThongBao() {
        List<ThongBaoResponse> thongBaoList = thongBaoService.getAllThongBao();
        return ResponseEntity.ok(ApiResponse.<List<ThongBaoResponse>>builder()
                .Code(200)
                .Message("Lấy danh sách thông báo thành công")
                .result(thongBaoList)
                .build());
    }
    
    // Lấy thông báo theo sinh viên
    @GetMapping("/sinh-vien/{maSV}")
    public ResponseEntity<ApiResponse<List<ThongBaoResponse>>> getThongBaoByMaSV(@PathVariable String maSV) {
        List<ThongBaoResponse> thongBaoList = thongBaoService.getThongBaoByMaSV(maSV);
        return ResponseEntity.ok(ApiResponse.<List<ThongBaoResponse>>builder()
                .Code(200)
                .Message("Lấy thông báo cho sinh viên thành công")
                .result(thongBaoList)
                .build());
    }
    
    // Lấy thông báo theo tag
    @GetMapping("/tag/{tag}")
    public ResponseEntity<ApiResponse<List<ThongBaoResponse>>> getThongBaoByTag(@PathVariable String tag) {
        List<ThongBaoResponse> thongBaoList = thongBaoService.getThongBaoByTag(tag);
        return ResponseEntity.ok(ApiResponse.<List<ThongBaoResponse>>builder()
                .Code(200)
                .Message("Lấy thông báo theo tag thành công")
                .result(thongBaoList)
                .build());
    }
    
    // Lấy chi tiết thông báo
    @GetMapping("/{maThongBao}")
    public ResponseEntity<ApiResponse<ThongBaoResponse>> getThongBaoById(@PathVariable String maThongBao) {
        ThongBaoResponse thongBao = thongBaoService.getThongBaoById(maThongBao);
        return ResponseEntity.ok(ApiResponse.<ThongBaoResponse>builder()
                .Code(200)
                .Message("Lấy chi tiết thông báo thành công")
                .result(thongBao)
                .build());
    }
}
