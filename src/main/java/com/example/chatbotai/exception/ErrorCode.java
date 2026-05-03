package com.example.chatbotai.exception;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_NOTEXISTED(201,"User notexisted"),
    UNAUTHENTICATED(401, "Unauthenticated"),
    UNAUTHORIZED(403, "You do not have permission"),
    INVALID_PASSWORD(400, "Invalid password")
    ;
    int code;
    String message;
}
