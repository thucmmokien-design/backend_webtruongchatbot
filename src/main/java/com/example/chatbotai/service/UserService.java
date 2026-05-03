package com.example.chatbotai.service;

import com.example.chatbotai.dto.requests.ChangePasswordRequest;
import com.example.chatbotai.dto.requests.UserRequests;
import com.example.chatbotai.Entity.TaiKhoan;
import com.example.chatbotai.exception.AppExcepsion;
import com.example.chatbotai.exception.ErrorCode;
import com.example.chatbotai.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    public boolean checklogin(UserRequests requests) {
        if (!userRepository.existsByUsernameAndPassword(
                requests.getUsername(),
                requests.getPassword())) {
            throw new AppExcepsion(ErrorCode.USER_NOTEXISTED);
        }
        return true;
    }

    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {
        TaiKhoan taiKhoan = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));
        
        if (!taiKhoan.getPassword().equals(request.getOldPassword())) {
            throw new AppExcepsion(ErrorCode.INVALID_PASSWORD);
        }
        
        taiKhoan.setPassword(request.getNewPassword());
        userRepository.save(taiKhoan);
    }
}
