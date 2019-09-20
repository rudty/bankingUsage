package com.rudtyz.bank.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*

/**
 * JWT 인증관련
 * 최대 15분까지 인증가능
 */
@Service
class JwtService {
    companion object {
        private const val SECRET_KEY = "7Lm07Lm07Jik7Y6Y7J20IOyduOy9lOuUqQ=="
        private const val EXPIRE_TIME = 900 * 1000
    }

    fun encode(vararg keyValue: Pair<String, Any>) = encode(mapOf(*keyValue))

    fun encode(claims: Map<String, Any>): String = Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact()

    /**
     * Map 형식, throw 시 비 정상적 인증 참고
     */
    fun decode(jwtString: String): Claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parse(jwtString)
                .body as Claims
}