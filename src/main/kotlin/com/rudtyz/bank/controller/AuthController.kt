package com.rudtyz.bank.controller

import com.rudtyz.bank.service.UserSignService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth")
class AuthController(
        private val userSignService: UserSignService
) {

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER_HEADER = "Bearer "
    }

    /**
     * signup 계정생성 API: ID, PW를 입력 받아 내부 DB에 계정을 저장하고 토큰을 생성하여 출력한다.
     * § 단, 패스워드는 안전한 방법으로 저장한다.
     * § 단, 토큰은 특정 secret으로 서명하여 생성한다.
     */
    @PostMapping("/signup")
    fun singleSignUp(response: HttpServletResponse, userId: String, pw: String): Any {
        response.addHeader(AUTHORIZATION, BEARER_HEADER + userSignService.signUp(userId, pw))
        return "OK"
    }

    /**
     * signin 로그인 API: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰을 발급한다.
     */
    @PostMapping("/signin")
    fun singleSignIn(response: HttpServletResponse, userId: String, pw: String): Any {
        response.addHeader(AUTHORIZATION, BEARER_HEADER + userSignService.signIn(userId, pw))
        return "OK"
    }

    /**
     * refresh 토큰 재발급 API: 기존에 발급받은 토큰을 Authorization 헤더에 “Bearer Token”으로 입력 요청을 하면 토큰을 재발급한다.
     */
    @PostMapping("/refresh")
    fun singleRefresh(@RequestHeader(AUTHORIZATION) authorization: String, response: HttpServletResponse): Any {
        response.addHeader(AUTHORIZATION, BEARER_HEADER + userSignService.refresh(authorization))
        return "OK"
    }
}