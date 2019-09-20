package com.rudtyz.bank.controller

import com.rudtyz.bank.service.UserSignService
import com.rudtyz.bank.util.setBearerAuth
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth")
class AuthController(
        private val userSignService: UserSignService
) {
    /**
     * signup 계정생성 API: ID, PW를 입력 받아 내부 DB에 계정을 저장하고 토큰을 생성하여 출력한다.
     * § 단, 패스워드는 안전한 방법으로 저장한다.
     * § 단, 토큰은 특정 secret으로 서명하여 생성한다.
     */
    @PostMapping("/signup")
    fun singleSignUp(response: HttpServletResponse, userId: String, pw: String): Any {
        response.setBearerAuth(userSignService.signUp(userId, pw))
        return "OK"
    }

    /**
     * signin 로그인 API: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰을 발급한다.
     */
    @PostMapping("/signin")
    fun singleSignIn(response: HttpServletResponse, userId: String, pw: String): Any {
        response.setBearerAuth(userSignService.signIn(userId, pw))
        return "OK"
    }

    /**
     * refresh 토큰 재발급 API: 기존에 발급받은 토큰을 Authorization 헤더에 “Bearer Token”으로 입력 요청을 하면 토큰을 재발급한다.
     */
    @PostMapping("/refresh")
    fun singleRefresh(@RequestAttribute("token")  tokenMap: Map<String, Any>, response: HttpServletResponse): Any {
        response.setBearerAuth(userSignService.refresh(tokenMap))
        return "OK"
    }
}