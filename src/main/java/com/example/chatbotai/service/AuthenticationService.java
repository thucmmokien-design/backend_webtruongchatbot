package com.example.chatbotai.service;

import com.example.chatbotai.dto.requests.AuthenticationRequest;
import com.example.chatbotai.dto.response.AuthenticationResponse;
import com.example.chatbotai.exception.AppExcepsion;
import com.example.chatbotai.exception.ErrorCode;
import com.example.chatbotai.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppExcepsion(ErrorCode.USER_NOTEXISTED));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new AppExcepsion(ErrorCode.UNAUTHENTICATED);
        }

        var token = jwtService.generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
}

