package com.rudtyz.bank.util

import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

private val base64Encoder = Base64.getEncoder()
fun encodeSha512(s: String): String{
    val sha512digest = MessageDigest.getInstance("SHA-512")
    val b = s.toByteArray(Charset.forName("UTF-8"))
    return base64Encoder.encodeToString(sha512digest.digest(b))
}