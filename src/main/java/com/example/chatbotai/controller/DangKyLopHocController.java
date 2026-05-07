package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.requests.DangKyLopHocRequest;
import com.example.chatbotai.dto.response.DangKyLopHocResponse;
import com.example.chatbotai.dto.response.LopHocDuocMoResponse;
import com.example.chatbotai.service.DangKyLopHocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dang-ky-lop-hoc")
@RequiredArgsConstructor
public class DangKyLopHocController {
    
    private final DangKyLopHocService dangKyLopHocService;
    
    /**
     * API 1: Lấy danh sách tất cả lớp học được mở theo học kỳ
     * Kèm theo trạng thái đăng ký của sinh viên
     */
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResponse<List<LopHocDuocMoResponse>>> getDanhSachLopHoc(
            @RequestParam(required = false) String maHocKy,
            @RequestParam(required = false) String namHoc,
            @RequestParam(required = false) String maSV) {
        List<LopHocDuocMoResponse> danhSach = dangKyLopHocService.getDanhSachLopHoc(maHocKy, namHoc, maSV);
        return ResponseEntity.ok(ApiResponse.<List<LopHocDuocMoResponse>>builder()
                .Code(200)
                .Message("Lấy danh sách lớp học thành công")
                .result(danhSach)
                .build());
    }
    
    /**
     * Lấy danh sách lớp học còn chỗ trống
     */
    @GetMapping("/danh-sach-con-cho")
    public ResponseEntity<ApiResponse<List<LopHocDuocMoResponse>>> getDanhSachLopConCho(
            @RequestParam(required = false) String maHocKy,
            @RequestParam(required = false) String namHoc,
            @RequestParam(required = false) String maSV) {
        List<LopHocDuocMoResponse> danhSach = dangKyLopHocService.getDanhSachLopConCho(maHocKy, namHoc, maSV);
        return ResponseEntity.ok(ApiResponse.<List<LopHocDuocMoResponse>>builder()
                .Code(200)
                .Message("Lấy danh sách lớp học còn chỗ trống thành công")
                .result(danhSach)
                .build());
    }
    
    /**
     * API 2: Đăng ký lớp học - chuyển trạng thái sang "Chờ duyệt"
     */
    @PostMapping("/dang-ky")
    public ResponseEntity<ApiResponse<DangKyLopHocResponse>> dangKyLopHoc(
            @RequestBody DangKyLopHocRequest request) {
        try {
            DangKyLopHocResponse response = dangKyLopHocService.dangKyLopHoc(request);
            return ResponseEntity.ok(ApiResponse.<DangKyLopHocResponse>builder()
                    .Code(200)
                    .Message("Đăng ký lớp học thành công")
                    .result(response)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<DangKyLopHocResponse>builder()
                    .Code(400)
                    .Message(e.getMessage())
                    .result(null)
                    .build());
        }
    }
    
    /**
     * API 3: Hủy đăng ký lớp học - xóa bản ghi (trạng thái về null)
     */
    @DeleteMapping("/huy-dang-ky/{idDangKy}")
    public ResponseEntity<ApiResponse<DangKyLopHocResponse>> huyDangKy(
            @PathVariable Integer idDangKy) {
        try {
            DangKyLopHocResponse response = dangKyLopHocService.huyDangKy(idDangKy);
            return ResponseEntity.ok(ApiResponse.<DangKyLopHocResponse>builder()
                    .Code(200)
                    .Message("Hủy đăng ký thành công")
                    .result(response)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<DangKyLopHocResponse>builder()
                    .Code(400)
                    .Message(e.getMessage())
                    .result(null)
                    .build());
        }
    }
    
    /**
     * Lấy danh sách đăng ký của sinh viên
     */
    @GetMapping("/sinh-vien/{maSV}")
    public ResponseEntity<ApiResponse<List<DangKyLopHocResponse>>> getDanhSachDangKy(
            @PathVariable String maSV) {
        List<DangKyLopHocResponse> danhSach = dangKyLopHocService.getDanhSachDangKy(maSV);
        return ResponseEntity.ok(ApiResponse.<List<DangKyLopHocResponse>>builder()
                .Code(200)
                .Message("Lấy danh sách đăng ký thành công")
                .result(danhSach)
                .build());
    }
}
