package com.fengxi.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: JwtUtils
 * @projectName demo
 * @date 2023/1/22 3:11:54
 */
public class JwtUtil {
    /**
     * 获取token中的参数
     *
     * @param token
     * @return
     */
    public static Claims parseToken(String token, String key) {
        if ("".equals(token)) {
            return null;
        }

        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    public static String createToken(Long userId, String username, String key, int expireMinutes) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId) // 设置载荷信息
                .claim("username", username)
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())// 设置超时时间
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }
}
