package com.rudtyz.bank.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class DeviceUsageRepositoryTests {

    @Autowired
    lateinit var deviceUsageService: DeviceUsageService

    @Test
    fun 정상_2017() {
        val r0 = deviceUsageService.getDeviceUsage(2017)
        Assert.assertNotEquals(r0, null)
        Assert.assertEquals(r0.year, 2017)
        Assert.assertEquals(r0.rate, 90.6, 0.5)
    }

    @Test
    fun 정상_2018() {
        val r0 = deviceUsageService.getDeviceUsage(2018)
        Assert.assertNotEquals(r0, null)
        Assert.assertEquals(r0.year, 2018)
        Assert.assertEquals(r0.rate, 90.5, 0.5)
    }

    @Test
    fun 에러_2010() {
        try {
            deviceUsageService.getDeviceUsage(2010)
            Assert.assertTrue(false)
        } catch (e: Exception) {
        }
    }

    @Test
    fun 테이블리로드() {
        deviceUsageService.reloadTable()
    }
}
