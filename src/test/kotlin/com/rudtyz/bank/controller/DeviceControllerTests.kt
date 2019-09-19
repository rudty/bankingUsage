package com.rudtyz.bank.controller


import org.apache.tomcat.util.json.JSONParser
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun 기본_2011_연결_확인() {
        mvc.perform(MockMvcRequestBuilders
                .get("/device/rank/year/2011")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.year").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.device_name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.rate").exists())
                .andReturn()
    }

    @Test
    fun 없는_데이터() {
        mvc.perform(MockMvcRequestBuilders
                .get("/device/rank/year/2001")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound)
    }

    @Test
    fun 모든디바이스() {
        mvc.perform(MockMvcRequestBuilders
                .get("/device/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.devices[*].device_id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.devices[*].device_name").exists())
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun 모든디바이스_그리고2011() {
        val result = mvc.perform(MockMvcRequestBuilders
                .get("/device/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.devices[*].device_id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.devices[*].device_name").exists())
                .andReturn().response.contentAsString

        val mapResult = JSONParser(result).parseObject()
        val firstElem = (mapResult["devices"] as ArrayList<*>)[0]
        val firstMap = firstElem as Map<String, Any>
        val firstDeviceId = firstMap["device_id"]

        mvc.perform(MockMvcRequestBuilders
                .get("/device/rank/id/$firstDeviceId")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.year").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.device_name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.rate").exists())

    }



    @Test
    fun 테이블_리로드() {
        mvc.perform(MockMvcRequestBuilders
                .get("/device/reload")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isString)
    }

}
