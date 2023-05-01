package cn.coisini.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTest {

    public static void main(String[] args) {

        //获取系统的当前时间
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);

        //生成jwt令牌
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("0916")//设置jwt编码
                .setSubject("coisini")//设置jwt主题
                .setIssuedAt(new Date())//设置jwt签发日期
                //.setExpiration(date)//设置jwt的过期时间
                .claim("roles","admin")
                .claim("company","coisini")
                .signWith(SignatureAlgorithm.HS256, "coisini");

        //生成jwt
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);

        //解析jwt,得到其内部的数据
        Claims claims = Jwts.parser().setSigningKey("coisini").parseClaimsJws(jwtToken).getBody();
        System.out.println(claims);
    }
}
