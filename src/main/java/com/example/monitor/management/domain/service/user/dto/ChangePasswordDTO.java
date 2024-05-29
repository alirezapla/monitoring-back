package com.example.monitor.management.domain.service.user.dto;

public class ChangePasswordDTO {
    private String userId;
    private String currentPassword;
    private String newPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
