package com.example.chatbotai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class VNPayConfig {
    
    @Value("${vnpay.url:https://sandbox.vnpayment.vn/paymentv2/vpcpay.html}")
    private String vnpayUrl;
    
    @Value("${vnpay.tmn-code:}")
    private String tmnCode;
    
    @Value("${vnpay.secret-key:}")
    private String secretKey;
    
    @Value("${vnpay.return-url:http://localhost:8080/api/vnpay/return}")
    private String returnUrl;
    
    @Value("${vnpay.api-url:https://sandbox.vnpayment.vn/merchant_webapi/api/transaction}")
    private String apiUrl;
    
    public String getVnpayUrl() {
        return vnpayUrl;
    }
    
    public String getTmnCode() {
        return tmnCode;
    }
    
    public String getSecretKey() {
        return secretKey;
    }
    
    public String getReturnUrl() {
        return returnUrl;
    }
    
    public String getApiUrl() {
        return apiUrl;
    }
    
    public String hmacSHA512(String data) {
        try {
            if (secretKey == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = secretKey.getBytes();
            final SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKeySpec);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
