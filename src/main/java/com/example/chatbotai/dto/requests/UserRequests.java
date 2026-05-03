package com.example.chatbotai.dto.requests;

import com.example.chatbotai.Entity.SinhVien;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequests {
    String username;
    String password;
    String role;
    SinhVien sinhVien;
}
