package com.example.chatbotai.controller;

import com.example.chatbotai.dto.requests.ApiResponse;
import com.example.chatbotai.dto.requests.UserRequests;
import com.example.chatbotai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dangnhap")
public class UserController {
    @Autowired
    UserService userService;
    
    @PostMapping("/checklogin")
    public String checkLogin(@RequestBody UserRequests requests){
        if(userService.checklogin(requests)){
            return "success";
        }
        return "fail";
    }
}
