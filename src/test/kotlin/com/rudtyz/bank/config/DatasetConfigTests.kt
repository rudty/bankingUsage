package com.rudtyz.bank.config

import com.rudtyz.bank.model.DeviceUsage
import com.rudtyz.bank.repository.local.DeviceUsageRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import javax.validation.Validation
import javax.validation.Validator

@RunWith(SpringRunner::class)
@SpringBootTest
class DatasetConfigTests {

    @Autowired
    lateinit var deviceUsageRepository: DeviceUsageRepository

    @Autowired
    lateinit var configDir: String

    @Test
    fun 파일_DATA_CSV_파싱_성공() {
        Assert.assertNotEquals(deviceUsageRepository.count(), 0)
    }

    @Test
    fun 설정_저장_경로() {
        Assert.assertNotEquals(configDir.length, 0)
    }
}
