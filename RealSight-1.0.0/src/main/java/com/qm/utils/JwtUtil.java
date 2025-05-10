package com.qm.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

    // 密钥配置（建议从环境变量读取）
    private static final String SECRET_KEY_STRING = "your-256-bit-secret-key-base64-encoded";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // Token有效期（单位：毫秒）
    public static final long EXPIRATION_TIME = 3600000L; // 1小时
    /**
     *
     * 生成JWT Token
     * @param subject 用户标识（如用户ID）
     * @param claims 自定义声明（如角色、权限）
     * @return JWT Token字符串
     */
    public static String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuer("xx")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY) // 自动识别HS256算法[5](@ref)
                .compact();
    }

    /**
     * 现在自己用的token生成
     * @param subject
     * @param ttlMillis
     * @param uuid
     * @return
     */
    public static String generateToken(String subject, Long ttlMillis, String uuid) {
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.EXPIRATION_TIME;
        }
        return Jwts.builder()
                .setId(uuid)
                .subject(subject)
                .issuer("xx")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ttlMillis))
                .signWith(SECRET_KEY) // 自动识别HS256算法[5](@ref)
                .compact();

    }

    public static String createJWT(String id, String subject, Long ttlMillis) {

        return generateToken(subject, ttlMillis, id);
    }

    public static String createJWT(String subject, Long ttlMillis) {

        return generateToken(subject, ttlMillis, getUUID());
    }

    // 生成令牌
    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
            * 解析并验证Token
     * @param token JWT Token字符串
     * @return 解析后的Claims对象
     * @throws ExpiredJwtException Token过期
     * @throws UnsupportedJwtException Token格式错误
     * @throws MalformedJwtException Token结构损坏
     * @throws SecurityException 签名验证失败
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     *
     * 快速生成Token（含默认声明）
     * @param userId 用户ID
     * @param roles 用户角色列表
     */
    public static String quickGenerate(String userId, String... roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return generateToken(userId, claims);
    }

    // 示例用法
    public static void main(String[] args) {
        // 生成Token
        String token = quickGenerate("user123", "admin", "editor");
        System.out.println("Generated Token: " + token);

        // 解析Token
        Claims claims = parseToken(token);
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Roles: " + claims.get("roles"));
    }
}