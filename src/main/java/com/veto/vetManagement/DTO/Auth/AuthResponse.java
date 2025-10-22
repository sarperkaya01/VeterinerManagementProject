package com.veto.vetManagement.DTO.Auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String username;
    private String role;
    private String message;
}
