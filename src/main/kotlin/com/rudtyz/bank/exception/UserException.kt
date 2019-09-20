package com.rudtyz.bank.exception

import java.lang.RuntimeException

/**
 * 유저 아이디가 이미 존재
 */
class UserIdExistsException(arg: String): RuntimeException("exists $arg")

/**
 * id 혹은 비밀번호 오류
 */
class LoginFailIdException(arg: String): RuntimeException("login error $arg")

/**
 * id 혹은 비밀번호 오류
 */
class LoginFailPasswordException(arg: String): RuntimeException("login error $arg")