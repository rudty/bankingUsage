package com.rudtyz.bank.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.Configuration

@Aspect
@Configuration
class ResponseWrapper {
    /**
     * 모든 단일 반환값에는 JSON 형식으로
     *  {
     *      "result": 내용
     *  }
     *  과 같이 반환, 일괄적으로 위와같은 형식으로 만들어준다.
     */
    @Around("execution(* com.rudtyz.bank.controller.*.single*(..))")
    fun singleResultWrapper(joinPoint: ProceedingJoinPoint): Any? {
        try {
            val result = joinPoint.proceed()
            return mapOf("result" to result)
        } catch (e: Throwable) {
            throw e;
        }
    }

    /**
     * 모든 단일 반환값에는 JSON 형식으로
     *  {
     *      "devices": 내용
     *  }
     *  과 같이 반환, 일괄적으로 위와같은 형식으로 만들어준다.
     */
    @Around("execution(* com.rudtyz.bank.controller.*.list*(..))")
    fun listResultWrapper(joinPoint: ProceedingJoinPoint): Any? {
        try {
            val result = joinPoint.proceed()
            return mapOf("devices" to result)
        } catch (e: Throwable) {
            throw e;
        }
    }

    /**
     * 모든 단일 반환값에는 JSON 형식으로
     *  {
     *      "error": 내용
     *  }
     *  과 같이 반환, 일괄적으로 위와같은 형식으로 만들어준다.
     */
    @Around("execution(* com.rudtyz.bank.exception.ErrorController.error*(..))")
    fun errorResultWrapper(joinPoint: ProceedingJoinPoint): Any? {
        try {
            val result = joinPoint.proceed()
            return mapOf("error" to result)
        } catch (e: Throwable) {
            throw e;
        }
    }
}