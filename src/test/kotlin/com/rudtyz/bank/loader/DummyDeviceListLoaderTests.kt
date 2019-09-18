package com.rudtyz.bank.loader

import org.junit.Assert
import org.junit.Test

class DummyDeviceListLoaderTests {

    @Test
    fun 중복_확률_적은_아이디_만들기() {
        val loader = DummyDeviceListLoader()
        val distinct = HashSet<String>()
        for (i in 1..100000) {
            distinct += loader.newID()
        }
        Assert.assertTrue(distinct.size > (100000 - 1000))
    }
}