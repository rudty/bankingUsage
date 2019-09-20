package com.rudtyz.bank.util

import org.junit.Assert
import org.junit.Test

class IdGeneratorTests {

    @Test
    fun 중복_확률_적은_아이디_만들기() {
        val distinct = HashSet<String>()
        for (i in 1..1000) {
            distinct += newID()
        }

        print(distinct.size)
        Assert.assertTrue(distinct.size > (1000 - 100))
    }
}