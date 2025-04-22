package com.portfolio.resource.user.controller;

import com.portfolio.resource.common.response.ApiResponse;
import com.portfolio.resource.security.Authenticated;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UserController {

    public UserController() {
    }
    @Authenticated
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Map<String, Object>>>  me(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> userInfo = new LinkedHashMap<>();
        userInfo.put("subject", jwt.getSubject());
        userInfo.put("issuedAt", jwt.getIssuedAt());
        userInfo.put("expiresAt", jwt.getExpiresAt());
        userInfo.put("clientId", jwt.getClaimAsString("client_id"));
        userInfo.put("scopes", jwt.getClaimAsStringList("scope"));

        return ApiResponse.OK(userInfo);
    }
}
