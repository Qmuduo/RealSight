package com.qm.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT工具类，用于生成和解析JWT令牌
 */
public class NewJwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 从 yml 注入值到实例变量
    @Value("${jwt.secret-key}")
    private String injectedSecretKey;

    // 从环境变量或配置文件中读取密钥
    // private static final String SECRET_KEY_STRING = System.getProperty("jwt.secret.key","your-256-bit-secret-key-base64-encoded"); // 默认值仅用于开发环境

    private static String SECRET_KEY_STRING; // 改为非 final



    // 通过 @PostConstruct 将实例变量值赋给静态变量
    @PostConstruct
    private void init() {
        SECRET_KEY_STRING = injectedSecretKey;
    }


    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // Token有效期（单位：毫秒）
    public static final long DEFAULT_EXPIRATION_TIME = 3600000L; // 1小时

    /**
     * 生成JWT Token
     * @param subject 用户标识（如用户ID）
     * @param claims 自定义声明（如角色、权限）
     * @param ttlMillis 有效期（毫秒），如果为null则使用默认值
     * @param uuid 唯一标识符，如果为null则自动生成
     * @return JWT Token字符串
     */
    public static String generateToken(String subject, Map<String, Object> claims, 
                                      Long ttlMillis, String uuid) {
        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty");
        }
        
        if (ttlMillis == null) {
            ttlMillis = DEFAULT_EXPIRATION_TIME;
        }
        
        if (uuid == null) {
            uuid = getUUID();
        }
        
        JwtBuilder builder = Jwts.builder()
                .id(uuid)
                .subject(subject)
                .issuer("xx")
                .issuedAt(new Date());
                
        // 添加自定义声明
        if (claims != null) {
            builder.claims(claims);
        }
        
        return builder
                .expiration(new Date(System.currentTimeMillis() + ttlMillis))
                .signWith(SECRET_KEY)
                .compact();
    }
    
    /**
     * 生成JWT Token（使用默认参数）
     * @param subject 用户标识（如用户ID）
     * @param claims 自定义声明（如角色、权限）
     * @return JWT Token字符串
     */
    public static String generateToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, null, null);
    }
    
    /**
     * 生成JWT Token（指定有效期和UUID）
     * @param subject 用户标识
     * @param ttlMillis 有效期（毫秒）
     * @param uuid 唯一标识符
     * @return JWT Token字符串
     */
    public static String generateToken(String subject, Long ttlMillis, String uuid) {
        return generateToken(subject, null, ttlMillis, uuid);
    }

    /**
     * 创建JWT（兼容旧方法）
     * @param id 唯一标识符
     * @param subject 用户标识
     * @param ttlMillis 有效期（毫秒）
     * @return JWT Token字符串
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        return generateToken(subject, null, ttlMillis, id);
    }

    /**
     * 创建JWT（兼容旧方法）
     * @param subject 用户标识
     * @param ttlMillis 有效期（毫秒）
     * @return JWT Token字符串
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return generateToken(subject, null, ttlMillis, null);
    }

    /**
     * 生成UUID
     * @return 不含连字符的UUID字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 解析并验证Token
     * @param token JWT Token字符串
     * @return 解析后的Claims对象
     * @throws JwtException 当Token解析失败时抛出
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException e) {
            logger.error("Token格式错误: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Token解析异常: {}", e.getMessage());
            throw new JwtException("Token解析失败", e);
        }
    }
    
    /**
     * 验证Token是否有效
     * @param token JWT Token字符串
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检查Token是否过期
     * @param token JWT Token字符串
     * @return 是否已过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            logger.error("检查Token过期状态时发生错误: {}", e.getMessage());
            return true;
        }
    }

    /**
     * 快速生成Token（含角色声明）
     * @param userId 用户ID
     * @param roles 用户角色列表
     * @return JWT Token字符串
     */
    public static String quickGenerate(String userId, String... roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return generateToken(userId, claims);
    }
}
