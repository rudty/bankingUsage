package com.rudtyz.bank.interceptor

import com.rudtyz.bank.service.JwtService
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * JWT 검증 확인
 * 해당 인터셉터 등록은 AuthInterceptorConfig 참고할 것
 */
@Component
class JwtInterceptor(
        val jwtService: JwtService
): HandlerInterceptor {

    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader(AUTHORIZATION)
        val authTokens = token.split(" ")

        if (authTokens[0] == "Bearer") {
            /*val parse = */jwtService.decode(authTokens[1])
            // 실제 서비스일떄는 여기서 추가로 인증 수행
            // (선택) 토큰 갱신하여 재발급 
            return true
        }
        return false
    }
}