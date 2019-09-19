package com.rudtyz.bank.util

/**
 * https://software.intel.com/en-us/articles/fast-random-number-generator-on-the-intel-pentiumr-4-processor/
 *
 * 서버가 여러개일 때는 각 서버의 id 혹은 ip(a.b.c.d 에서 d), 프로세스 아이디(ProcessHandle.current().pid()) 등을
 * 추가하는 것으로 중복을 삭제할 것
 */
private var seed = System.currentTimeMillis()

private fun randomValue(): Long {
    seed = (214013 * seed + 2531011);
    return seed shr 16 and 0x7FFF
}

fun newID() = "DIS" + System.currentTimeMillis().toString(16).substring(6) + randomValue()
