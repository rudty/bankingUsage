package com.rudtyz.bank.config

import com.rudtyz.bank.interceptor.JwtInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthInterceptorConfig(
        private val jwtInterceptor: JwtInterceptor
): WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/device/**")
                .addPathPatterns("/auth/refresh")
    }
}