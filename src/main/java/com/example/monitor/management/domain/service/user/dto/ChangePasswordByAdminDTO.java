package com.example.monitor.management.domain.service.user.dto;

public class ChangePasswordByAdminDTO {
    private String userId;
    private String newPassword;

    public String getUserId() {
        return userId;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
