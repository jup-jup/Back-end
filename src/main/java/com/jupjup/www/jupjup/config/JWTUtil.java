package com.jupjup.www.jupjup.config;

import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.NoSuchElementException;

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

    public static String generateAccessToken(Long userId, String userName, String userEmail) {
        return generateToken(userId, userName, userEmail, accessEncKey, expirationTime);
    }

    public static String generateRefreshToken(Long userId, String userName, String userEmail) {
        return generateToken(userId, userName, userEmail, refreshEncKey, refreshExpirationTime);
    }

    private static String generateToken(Long userId, String userName, String userEmail,  Key key, long expirationTime) {

        Date refreshDate = new Date(System.currentTimeMillis() + refreshExpirationTime);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .subject(userName)
                .subject(userEmail)
                // 액세스 토큰 발급 시 리프레시 만료시간 같이 보내 DB접근 줄이기
                .subject(String.valueOf(refreshDate))
                .issuedAt(new Date(System.currentTimeMillis()))  // 토큰 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시
                .signWith(key)
                .compact();
    }


    // 리프레시 토큰 DB 저
    public static Date RefreshTokenExTimeCul(String refresh) {
        // 현재 시간에 만료 시간을 더하여 Date 객체 생성
        return new Date(System.currentTimeMillis() + refreshExpirationTime);

    }

    public static void validateAccessToken(String token) {
        validateToken(token, accessEncKey);
    }

    public static void validateRefreshToken(String token){
        validateToken(token, refreshEncKey);
    }

    private static String extractClaim(String token, Object keyValue, String value) {
        try {
            if (keyValue instanceof SecretKey key) {
                return Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .get(value, String.class);
            }
            throw new IllegalArgumentException("The provided keyValue is not a valid SecretKey.");
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 처리
            log.info("토큰 만료 {}", e.getMessage());
            throw e ;
        }
    }

    private static void validateToken(String token, SecretKey key) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
         expiration.before(new Date());
        } catch (NullPointerException e) {
            log.error("토큰이 비어 있습니다.", e);
        }
    }


    public static Long getUserIdFromAccessToken(String token) {
        return Long.valueOf(extractClaim(token, accessEncKey, "userId"));
    }

    public static String getUserNameFromAccessToken(String token) {
        return extractClaim(token, accessEncKey, "userName");
    }

    public static String getUserEmailFromAccessToken(String token) {
        return extractClaim(token, accessEncKey, "userEmail");
    }

    public static String getUserEmailFromRefreshToken(String token) {
        return extractClaim(token, refreshEncKey, "userEmail");
    }

    public static Long getUserIdFromRefreshToken(String token) {
        return Long.valueOf(extractClaim(token, accessEncKey, "userName"));
    }

    public static String getUserNameFromRefreshToken(String token) {
        return extractClaim(token, accessEncKey, "userName");
    }

    public static String getRoleFromRefreshToken(String token) {
        return extractClaim(token, refreshEncKey, "role");
    }

    public static Cookie getCookieFromAccessToken(String accessToken) {
        return createCookie(accessToken,"accessToken");

    }
    public static Cookie getCookieFromRefreshToken(String refreshToken) {
        return createCookie(refreshToken,"refreshToken");
    }

    public static Cookie createCookie(String token, String type) {
        Cookie toKen = new Cookie(type, token);
        toKen.setDomain("jupjup.shop");
        toKen.setHttpOnly(true); // JavaScript 에서 접근하지 못하도록 설정
        toKen.setPath("/"); // 하위 모든 경로 쿠키 유효
        toKen.setMaxAge(COOKIE); // 쿠키의 유효기간 설정 (30일)
        toKen.setSecure(true); // HTTP에서 사용 가능하게 설정
        return toKen;
    }


}