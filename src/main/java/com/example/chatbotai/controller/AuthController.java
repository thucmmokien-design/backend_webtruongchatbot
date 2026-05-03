package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.requests.AuthenticationRequest;
import com.example.chatbotai.dto.requests.ChangePasswordRequest;
import com.example.chatbotai.dto.requests.IntrospectRequest;
import com.example.chatbotai.dto.response.AuthenticationResponse;
import com.example.chatbotai.dto.response.IntrospectResponse;
import com.example.chatbotai.service.AuthenticationService;
import com.example.chatbotai.service.JwtService;
import com.example.chatbotai.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthenticationService authenticationService;
    JwtService jwtService;
    UserService userService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        var result = jwtService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PutMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        userService.changePassword(username, request);
        
        return ApiResponse.<String>builder()
                .result("Password changed successfully")
                .build();
    }
}
