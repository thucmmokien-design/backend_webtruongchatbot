package com.example.chatbotai.service;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class VietQRService {
    
    private static final String BANK_ID = "970418"; // Mã ngân hàng BIDV
    private static final String BANK_NAME = "BIDV";
    private static final String ACCOUNT_PREFIX = "V3HAU";
    
    /**
     * Tạo URL QR code chuyển khoản ngân hàng theo chuẩn VietQR
     * 
     * @param maSV Mã sinh viên
     * @param amount Số tiền cần thanh toán
     * @param maHocKy Mã học kỳ
     * @param namHoc Năm học
     * @return URL để tạo QR code
     */
    public String createBankTransferQR(String maSV, long amount, String maHocKy, String namHoc) {
        // Tạo số tài khoản: V3HAU + MSV
        String accountNumber = ACCOUNT_PREFIX + maSV;
        
        // Tạo nội dung chuyển khoản
        String description = "HP " + maHocKy + " " + namHoc + " SV " + maSV;
        
        try {
            // Encode description để tránh lỗi với ký tự đặc biệt
            String encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8.toString());
            
            // Tạo URL QR code theo chuẩn VietQR API
            // Format: https://img.vietqr.io/image/{BANK_ID}-{ACCOUNT_NUMBER}-{TEMPLATE}.jpg?amount={AMOUNT}&addInfo={DESCRIPTION}
            String qrUrl = String.format(
                "https://img.vietqr.io/image/%s-%s-compact2.jpg?amount=%d&addInfo=%s&accountName=%s",
                BANK_ID,
                accountNumber,
                amount,
                encodedDescription,
                URLEncoder.encode("Truong Dai Hoc", StandardCharsets.UTF_8.toString())
            );
            
            return qrUrl;
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Tạo thông tin chuyển khoản dạng text
     */
    public BankTransferInfo getBankTransferInfo(String maSV, long amount, String maHocKy, String namHoc) {
        String accountNumber = ACCOUNT_PREFIX + maSV;
        String description = "HP " + maHocKy + " " + namHoc + " SV " + maSV;
        
        return BankTransferInfo.builder()
                .bankName(BANK_NAME)
                .bankId(BANK_ID)
                .accountNumber(accountNumber)
                .accountName("Truong Dai Hoc")
                .amount(amount)
                .description(description)
                .build();
    }
    
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class BankTransferInfo {
        private String bankName;
        private String bankId;
        private String accountNumber;
        private String accountName;
        private Long amount;
        private String description;
    }
}
