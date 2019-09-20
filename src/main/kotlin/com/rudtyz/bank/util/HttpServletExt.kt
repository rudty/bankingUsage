package com.rudtyz.bank.util

import org.springframework.http.HttpHeaders
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 인증 항목에 bearer가 있으면 해당 토큰을 반환
 * 없다면 null을 반환
 */
fun HttpServletRequest.getBearerAuthHeader(): String? {
   val token = getHeader(HttpHeaders.AUTHORIZATION) ?: return null
   val authTokens = token.split(" ")

   if (authTokens.size != 2 || authTokens[0] != "Bearer") {
      return null
   }

   return authTokens[1]
}

fun HttpServletResponse.setBearerAuth(v: String) {
   this.setHeader(HttpHeaders.AUTHORIZATION, "Bearer $v")
}