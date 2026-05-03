package com.example.chatbotai.dto.response;

import com.example.chatbotai.Entity.SinhVien;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequests {
    String username;
    String password;
    String role;
    SinhVien sinhVien;
}
