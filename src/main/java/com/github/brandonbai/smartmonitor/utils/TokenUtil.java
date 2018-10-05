package com.github.brandonbai.smartmonitor.utils;

import com.github.brandonbai.smartmonitor.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token工具类
 */
public class TokenUtil {

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static String createToken(User user) {
        return createJWT(user, 1000*3600);
    }

    public static boolean checkToken(String token) {
        if(token == null) {
            return false;
        }
        Claims claims = parseJWT(token);
        if(claims == null) {
            return false;
        }
        User user = new User();
        Object id = claims.get("id");
        if(id != null) {
            user.setId((Integer) id);
        }

        String username = (String) claims.get("username");
        user.setUsername(username);
        Integer roleId = (Integer) claims.get("roleId");
        user.setRoleId(roleId);
        String tel = (String) claims.get("tel");
        user.setTel(tel);
        userThreadLocal.set(user);

        return true;
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static String getUsername() {
        return getUser().getUsername();
    }

    private static String createJWT(User user, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Key signingKey = generalKey();

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("roleId", user.getRoleId());
        map.put("tel", user.getTel());
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(String.valueOf(user.getId()))
                .setClaims(map)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static SecretKey generalKey(){
        String stringKey = "smart-monitor";
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void remove() {
        userThreadLocal.remove();
    }
}
