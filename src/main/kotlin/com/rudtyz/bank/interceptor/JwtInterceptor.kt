package com.rudtyz.bank.interceptor

import com.rudtyz.bank.exception.AuthorizationKeyRequireException
import com.rudtyz.bank.service.JwtService
import com.rudtyz.bank.util.getBearerAuthHeader
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

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try {
            val token = request.getBearerAuthHeader() ?: throw AuthorizationKeyRequireException()
            request.setAttribute("token", jwtService.decode(token))

            // 실제 서비스일떄는 여기서 추가로 인증 수행
            // (선택) 토큰 갱신하여 재발급
            // response.setHeader(jwtService.encode())
        } catch (e: Exception) {
            //ErrorController.errorRequireApiKey
            request.getRequestDispatcher("/error/apikey").forward(request, response);
            return false
        }

        return true
    }
}