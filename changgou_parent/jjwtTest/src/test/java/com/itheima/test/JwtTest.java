package com.itheima.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

/*
 * @author 王昌耀
 * @date 2020/10/29 19:51
 */
public class JwtTest {

    //解析token
    @Test
    public void test1(){
        //定义token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzOTllZWNkYi02M2UwLTQ4YjItYTVhZC00MzhkZjM2M2ExMjgiLCJzdWIiOiJhZG1pbiIsImlzcyI6ImFkbWluIiwiaWF0IjoxNjAzOTc4MDA3LCJleHAiOjE2MDM5ODE2MDd9.ySUqDqrKwmE_dFUQSCmutA_V0CFwSBecpaHJ23ECp7g";
        //解析字符串
        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
        System.out.println("claims = " + claims);   //claims = {jti=777, sub=CLEARLOVE, iat=1603972193}
    }

    //设置过期时间
    @Test
    public void test2() {
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("888")
                .setSubject("777")
                .setIssuedAt(new Date())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256,"itheima");
        String token = jwtBuilder.compact();
        System.out.println("token = " + token);
    }

    //解析token
    @Test
    public void test3() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiI3NzciLCJpYXQiOjE2MDM5NzM2NDMsImV4cCI6MTYwMzk3MzY0M30.gYGfq1hkmqFv-Cm1wvayhOa4YAhTLnsmcwZSda6MiUw";
        Claims claims = Jwts.parser().setSigningKey("itheima").parseClaimsJws(token).getBody();
        System.out.println("claims = " + claims);
    }

    //自定义claims
    @Test
    public void test4() {
        long l = System.currentTimeMillis();
        Date date =new Date(l+1000000L);

        //创建jwt
        JwtBuilder jwtBuilder = Jwts.builder()
                //唯一编号
                .setId("999")
                //主题
                .setSubject("小白")
                //设置签名时间
                .setIssuedAt(new Date())
                //设置过期时间
                .setExpiration(date)
                //自定义角色
                .claim("role","admin")
                .claim("age",18)
                .claim("address","武汉")
                //参数1 算法  参数2 secretKey
                .signWith(SignatureAlgorithm.HS256,"itcast");

        String token = jwtBuilder.compact();
        System.out.println("token = " + token);
    }

    //解析token
    @Test
    public void test5() {
        //定义token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5OTkiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE2MDM5NzQ1ODMsImV4cCI6MTYwMzk3NTU4Mywicm9sZSI6ImFkbWluIiwiYWdlIjoxOCwiYWRkcmVzcyI6IuatpuaxiSJ9.BfcC0t2BlLeu-FhADhLAJe2PNiQ7LPuRHJAiac4SQHA";
        //解析token
        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
        System.out.println("claims = " + claims);
    }
}
