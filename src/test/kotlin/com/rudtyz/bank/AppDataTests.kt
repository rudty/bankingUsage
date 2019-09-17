package com.rudtyz.bank

import com.rudtyz.bank.model.DeviceUsage
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
class AppDataTests {

    @Autowired
    lateinit var deviceUsage: Map<Int, DeviceUsage>

    @Autowired
    lateinit var configDir: String

    @Autowired
    lateinit var validator: Validator

    @Test
    fun 파일_DATA_CSV_파싱_성공() {
        Assert.assertNotEquals(deviceUsage.size, 0)
    }

    @Test
    fun 설정_저장_경로() {
        Assert.assertNotEquals(configDir.length, 0)
    }

    @Test
    fun 유효성_검사() {
        val r = DeviceUsage( 2000, 0.0,1.0,2.0,3.0,4.0,999.0 )
        val v = Validation.buildDefaultValidatorFactory().validator
        val r0 = v.validate(r).forEach { println(it.message) }
        val r1 = v.validateProperty(v, "smartPadUsage")
        print(r0)
        print(r1)
    }


}
