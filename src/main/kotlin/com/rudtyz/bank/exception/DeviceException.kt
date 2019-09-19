package com.rudtyz.bank.exception

import java.lang.RuntimeException

/**
 * 디바이스 데이터가 없음
 */
class NoSuchDeviceDataException(arg: String): RuntimeException("NoSuchDeviceData $arg")

/**
 * 찾을 수 없는 디바이스 아이디
 */
class InvalidDeviceIdException(deviceId: String): Exception("InvalidDeviceIdException $deviceId")
