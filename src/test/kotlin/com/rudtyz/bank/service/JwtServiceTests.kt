package com.rudtyz.bank.service

import org.junit.Assert
import org.junit.Test

class JwtServiceTests {

    @Test
    fun jwt생성테스트() {
        val testKey = "ice cream sandwich"
        val testValue = "jelly bean"

        val jwtService = JwtService()
        val encodeString = jwtService.encode(testKey to testValue)

        println(encodeString)

        val decodeMap = jwtService.decode(encodeString)
        Assert.assertNotNull(decodeMap[testKey])
        Assert.assertEquals(decodeMap[testKey], testValue)
    }
}