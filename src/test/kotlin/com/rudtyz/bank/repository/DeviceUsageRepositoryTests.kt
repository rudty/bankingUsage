package com.rudtyz.bank.repository

import com.rudtyz.bank.repository.local.DeviceUsageRepository
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
    lateinit var deviceUsageRepository: DeviceUsageRepository

    @Test
    fun 정상확인2017() {
        val r0 = deviceUsageRepository.findByYearAndDeviceNameNotOrderByDescUsageLimit1(2017, "이용률")
        Assert.assertNotEquals(r0!!, null)
        Assert.assertEquals(r0.usage, 90.6, 0.5)
        Assert.assertEquals(r0.year, 2017)
    }

    @Test
    fun 정상확인2018() {
        val r0 = deviceUsageRepository.findByYearAndDeviceNameNotOrderByDescUsageLimit1(2018, "이용률")
        Assert.assertNotEquals(r0!!, null)
        Assert.assertEquals(r0.year, 2018)
    }
}