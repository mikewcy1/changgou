package com.itheima.test;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/*
 * @author 王昌耀
 * @date 2020/10/29 19:38
 */
public class TestJwt {
    public static void main(String[] args) {
        //创建jwt
        JwtBuilder jwtBuilder = Jwts.builder()
                //唯一编号
                .setId("777")
                //主题
                .setSubject("CLEARLOVE")
                //签发日期
                .setIssuedAt(new Date())
                //签名 参数1算法  参数2 设置SecretKey
                .signWith(SignatureAlgorithm.HS256, "itcast");
        System.out.println(jwtBuilder);

        //生成token
        String token = jwtBuilder.compact();
        System.out.println("token = " + token);
    }
}
