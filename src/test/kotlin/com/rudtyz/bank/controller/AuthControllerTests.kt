package com.rudtyz.bank.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.rudtyz.bank.model.BankUser
import com.rudtyz.bank.service.JwtService
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

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests {
    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var jwtService: JwtService

    @Test
    fun 회원가입() {
        mvc.perform(MockMvcRequestBuilders
                .post("/auth/signup")
                .param("userId", "test_user_id")
                .param("pw", "test_password")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        //아이디 중복해서 두번
        //실패
        mvc.perform(MockMvcRequestBuilders
                .post("/auth/signup")
                .param("userId", "test_user_id")
                .param("pw", "test_password")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError)
//                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun 회원가입_후_로그인() {
        mvc.perform(MockMvcRequestBuilders
                .post("/auth/signup")
                .param("userId", "test_user_id2")
                .param("pw", "test_password2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        mvc.perform(MockMvcRequestBuilders
                .post("/auth/signin")
                .param("userId", "test_user_id2")
                .param("pw", "test_password2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun 회원가입_후_토근_재발급() {
        mvc.perform(MockMvcRequestBuilders
                .post("/auth/signup")
                .param("userId", "test_user_i22")
                .param("pw", "test_password2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        mvc.perform(MockMvcRequestBuilders
                .post("/auth/refresh")
                .header("Authorization", "Bearer " + jwtService.encode("test" to "test"))
                .param("userId", "test_user_id2")
                .param("pw", "test_password2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }
}
