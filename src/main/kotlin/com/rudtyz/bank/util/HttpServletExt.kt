package com.rudtyz.bank.util

import org.springframework.http.HttpHeaders
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.setBearerAuth(v: String) {
   this.setHeader(HttpHeaders.AUTHORIZATION, "Bearer $v")
}