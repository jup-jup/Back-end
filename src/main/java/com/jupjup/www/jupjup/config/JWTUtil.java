package com.jupjup.www.jupjup.config;

import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * JWT 발행 및 유효성 검증
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${jwt.secret}")
    private String accessSecretKey;
    @Value("${jwt.refresh-secret}")
    private String refreshSecretKey;
    private static final long expirationTime = 24 * 60 * 60 * 1000; // 개발 환경 24시간
//    public static final long expirationTime = 60000; // 1분
    public static final long refreshExpirationTime = 7 * 24 * 60 * 60 * 1000L; // 7일
    public static final int COOKIE =30 * 24 * 60 * 60 ; // 30일

    private static SecretKey accessEncKey;
    private static SecretKey refreshEncKey;

    private final RefreshTokenRepository refreshRepository;

    @PostConstruct
    public void init() {
        accessEncKey = new SecretKeySpec(accessSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        refreshEncKey = new SecretKeySpec(refreshSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    public static String generateAccessToken(String userEmail, String role) {
        return generateToken(userEmail, role, accessEncKey, expirationTime);
    }

    public static String generateRefreshToken(String userEmail, String role) {
        return generateToken(userEmail, role, refreshEncKey, refreshExpirationTime);
    }

    private static String generateToken(String userEmail, String role, Key key, long expirationTime) {

        return Jwts.builder()
                .claim("userEmail", userEmail)
                .claim("role", role)
                // 액세스 토큰 발급 시 리프레시 만료시간 같이 보내 DB접근 줄이기
                .claim("refreshTokenExpiration", new Date(System.currentTimeMillis() + refreshExpirationTime))
                .issuedAt(new Date(System.currentTimeMillis()))  // 토큰 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시
                .signWith(key)
                .compact();
    }


    // 리프레시 토큰 DB 저
    public static Date RefreshTokenExTimeCul(String refresh) {
        // 현재 시간에 만료 시간을 더하여 Date 객체 생성
        Date expirationDate = new Date(System.currentTimeMillis() + refreshExpirationTime);
        return expirationDate;

    }

    public static void validateAccessToken(String token) {
        validateToken(token, accessEncKey);
    }

    public static boolean validateRefreshToken(String token){
        return validateToken(token, refreshEncKey);
    }

    private static String extractClaim(String token, Object keyValue, String role) {
        if (keyValue instanceof SecretKey key) {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get(role, String.class);
        }
        throw new NullPointerException("Invalid claim");
    }

    private static boolean validateToken(String token, SecretKey key) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("만료되었거나 유효하지 않은 토큰", e);
            return true; // 만료되었거나 유효하지 않은 토큰으로 간주
        } catch (NullPointerException e){
            log.error("토큰이 비어 있습니다.", e);
            return true;
        }
    }


    public static String getUsernameFromAccessToken(String token) {
        return extractClaim(token, accessEncKey, "userEmail");
    }

    public static String getRoleFromAccessToken(String token) {
        return extractClaim(token, accessEncKey, "role");
    }

    public static String getRoleFromRefreshToken(String token) {
        return extractClaim(token, refreshEncKey, "role");
    }


    public static Cookie createCookie(String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true); // JavaScript 에서 접근하지 못하도록 설정
        refreshTokenCookie.setSecure(true); // HTTPS 를 통해서만 전송되도록 설정
        refreshTokenCookie.setPath("/"); // 하위 모든 경로 쿠키 유효
        refreshTokenCookie.setMaxAge(COOKIE); // 쿠키의 유효기간 설정 (30일)
        return refreshTokenCookie;
    }


}