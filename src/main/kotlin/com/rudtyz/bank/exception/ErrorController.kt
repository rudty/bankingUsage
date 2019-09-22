package com.rudtyz.bank.exception

import com.rudtyz.bank.exception.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@ControllerAdvice
class ErrorController : org.springframework.boot.web.servlet.error.ErrorController{
    override fun getErrorPath() = "/error"

    @GetMapping("/error")
    fun onDefaultError() = "error"

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun errorAny(req: HttpServletRequest, ex: Exception): Any? = "INTERNAL ERROR 0xAC"

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun errorRuntime(req: HttpServletRequest, ex: RuntimeException): Any? = "INTERNAL ERROR 0xFA"

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchDeviceDataException::class)
    fun errorNoSuchDevice(req: HttpServletRequest, ex: NoSuchDeviceDataException): Any? = ex.message

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDeviceIdException::class)
    fun errorInvalidDeviceId(req: HttpServletRequest, ex: InvalidDeviceIdException): Any? = ex.message

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginFailIdException::class)
    fun errorLoginFailId(req: HttpServletRequest, ex: LoginFailIdException): Any? = ex.message

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginFailPasswordException::class)
    fun errorLoginFailPassword(req: HttpServletRequest, ex: LoginFailPasswordException): Any? = ex.message

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserIdExistsException::class)
    fun errorLoginIdExists(req: HttpServletRequest, ex: UserIdExistsException): Any? = ex.message

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthorizationKeyRequireException::class)
    fun errorRequireApiKey(req: HttpServletRequest, ex: AuthorizationKeyRequireException): Any? = ex.message

    @RequestMapping("/error/apikey")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun errorRequireApiKey(): Any? = "require API Key"
}