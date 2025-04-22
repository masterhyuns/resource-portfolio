package com.portfolio.resource.security;

import com.portfolio.resource.common.exception.CustomException;
import com.portfolio.resource.common.response.ApiResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationAspect {


    @Around("@annotation(com.portfolio.resource.security.Authenticated)")
    public Object injectAuthenticatedUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new CustomException(ApiResponseCode.INVALID_TOKEN);
        }

        String email = jwt.getClaimAsString("email");
        if (email == null) {
            throw new CustomException(ApiResponseCode.INVALID_TOKEN);
        }

        Object[] newArgs = Arrays.copyOf(joinPoint.getArgs(), joinPoint.getArgs().length + 1);

        return joinPoint.proceed(newArgs);
    }
}
