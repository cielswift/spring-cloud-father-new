package com.ciel.springcloudfathernewsso.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class JwtUtil {

    protected static Algorithm algorithm = Algorithm.HMAC256("xia-pei#xin$202"); //密钥

    protected static int time = 60; //60分钟

    /**
     * 生成Token
     * 
     * JWT分成3部分：1.头部（header),2.载荷（payload, 类似于飞机上承载的物品)，3.签证（signature)
     * 
     * 加密后这3部分密文的字符位数为：
     *  1.头部（header)：36位，Base64编码 
     *  2.载荷（payload)：没准，BASE64编码
     *  3.签证（signature)：43位，将header和payload拼接生成一个字符串，
     *                          使用HS256算法和我们提供的密钥（secret,服务器自己提供的一个字符串），
     *                          对str进行加密生成最终的JWT
     * @return
     * @throws Exception
     */

    public static String createToken(String load) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create().withHeader(map)  //生成 头
                /* 设置 载荷 Payload */
                .withClaim("body", load)
                .withIssuer("sso_server") // 签名是有谁生成 例如 服务器
                .withSubject("jwt_token")// 签名的主题
                // .withNotBefore(new Date())//定义在什么时间之前，该jwt都是不可用的
                .withAudience("user")// 签名的观众 也可以理解谁接受签名的
                .withIssuedAt(new Date()) // 生成签名的时间
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*time))
                /* 签名 Signature */
                .sign(algorithm);
            return token;
    }

    /**
     * 解析token
     * @param token
     * @return
     */

    public static String parseToken(String token){

        JWTVerifier verifier = JWT.require(algorithm) //验证
                .withIssuer("sso_server")
                .build();

        DecodedJWT jwt = null;
        try {
             jwt = verifier.verify(token); //获取值
        }catch (TokenExpiredException exception){
            throw new RuntimeException("token已过期");
        }catch (Exception exception){
            throw new RuntimeException("解析异常");
        }
        String subject = jwt.getSubject();

        List<String> audience = jwt.getAudience();
        Map<String, Claim> claims = jwt.getClaims();

        Claim body = claims.get("body");
        return body.asString();
    }

}
