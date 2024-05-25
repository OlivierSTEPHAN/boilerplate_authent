package com.zytoune.boilerplate_authent.dto;

public record ResetPasswordDTO (String code, String email, String password) {
}
