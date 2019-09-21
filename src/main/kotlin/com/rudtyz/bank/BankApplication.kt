package com.rudtyz.bank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class BankApplication

fun main(args: Array<String>) {
	runApplication<BankApplication>(*args)
}
