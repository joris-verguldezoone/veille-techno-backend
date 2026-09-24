package org.acme.auth.dto;

public class RegisterRequest {
    public String email;
    public String password;
    public String name;
} // Le reste sera attribué automatiquement
// Le role admin sera attribué par @Put