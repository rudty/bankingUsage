package com.rudtyz.bank.aop

import com.rudtyz.bank.model.logger.ProcessFail
import com.rudtyz.bank.repository.global.logger.ProcessFailRepository
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

/**
 * 처리 실패시 DB에 간단하게 이유 및 요청 정보등을 저장
 */
@Aspect
@Component
class LoggingAspect(
        private val processFailRepository: ProcessFailRepository
) {

    @Before("execution(* com.rudtyz.bank.exception.ErrorController.*(..))")
    fun writeFailLog(joinPoint: JoinPoint) {
        try {
            val args = joinPoint.args
            if (args.size == 2) {
                val req = args[0] as HttpServletRequest
                val ex = args[1] as Exception

                processFailRepository.save(
                        ProcessFail("${ex.message}",
                                req.method,
                                req.requestURL.toString(),
                                req.remoteAddr)
                )
                ex.printStackTrace()

            }
        } catch(e: Exception) {
            //ignore all
        }
    }
}