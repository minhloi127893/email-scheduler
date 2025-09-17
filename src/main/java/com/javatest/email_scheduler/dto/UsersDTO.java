package com.javatest.email_scheduler.dto;

import lombok.Data;

@Data
public class UsersDTO {
    private String name;
    private String password;
    private String confirmPassword; // có thể dùng để xác nhận mật khẩu
}

