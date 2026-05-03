package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.requests.UpdateNguoiGiamHoRequest;
import com.example.chatbotai.dto.requests.UpdateNguoiThanRequest;
import com.example.chatbotai.dto.requests.UpdateSinhVienRequest;
import com.example.chatbotai.dto.requests.UpdateThongTinCaNhanRequest;
import com.example.chatbotai.dto.response.NguoiGiamHoResponse;
import com.example.chatbotai.dto.response.NguoiThanResponse;
import com.example.chatbotai.dto.response.SinhVienResponse;
import com.example.chatbotai.dto.response.ThongTinCaNhanResponse;
import com.example.chatbotai.service.NguoiGiamHoService;
import com.example.chatbotai.service.NguoiThanService;
import com.example.chatbotai.service.SinhVienService;
import com.example.chatbotai.service.ThongTinCaNhanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sinhvien")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SinhVienController {
    SinhVienService sinhVienService;
    ThongTinCaNhanService thongTinCaNhanService;
    NguoiGiamHoService nguoiGiamHoService;
    NguoiThanService nguoiThanService;

    @GetMapping("/me")
    public ApiResponse<SinhVienResponse> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<SinhVienResponse>builder()
                .result(sinhVienService.getSinhVienByUsername(username))
                .build();
    }

    @PutMapping("/me")
    public ApiResponse<SinhVienResponse> updateMyInfo(@RequestBody UpdateSinhVienRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<SinhVienResponse>builder()
                .result(sinhVienService.updateSinhVienByUsername(username, request))
                .build();
    }

    @GetMapping("/me/thongtincanhan")
    public ApiResponse<ThongTinCaNhanResponse> getThongTinCaNhan() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<ThongTinCaNhanResponse>builder()
                .result(thongTinCaNhanService.getThongTinCaNhanByUsername(username))
                .build();
    }

    @PutMapping("/me/thongtincanhan")
    public ApiResponse<ThongTinCaNhanResponse> updateThongTinCaNhan(@RequestBody UpdateThongTinCaNhanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<ThongTinCaNhanResponse>builder()
                .result(thongTinCaNhanService.updateThongTinCaNhan(username, request))
                .build();
    }

    @GetMapping("/me/nguoigiamho")
    public ApiResponse<List<NguoiGiamHoResponse>> getNguoiGiamHo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<List<NguoiGiamHoResponse>>builder()
                .result(nguoiGiamHoService.getNguoiGiamHoByUsername(username))
                .build();
    }

    @PutMapping("/me/nguoigiamho")
    public ApiResponse<NguoiGiamHoResponse> updateNguoiGiamHo(@RequestBody UpdateNguoiGiamHoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<NguoiGiamHoResponse>builder()
                .result(nguoiGiamHoService.updateNguoiGiamHo(username, request))
                .build();
    }

    @GetMapping("/me/nguoithan")
    public ApiResponse<List<NguoiThanResponse>> getNguoiThan() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<List<NguoiThanResponse>>builder()
                .result(nguoiThanService.getNguoiThanByUsername(username))
                .build();
    }

    @PutMapping("/me/nguoithan")
    public ApiResponse<NguoiThanResponse> updateNguoiThan(@RequestBody UpdateNguoiThanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<NguoiThanResponse>builder()
                .result(nguoiThanService.updateNguoiThan(username, request))
                .build();
    }
}
