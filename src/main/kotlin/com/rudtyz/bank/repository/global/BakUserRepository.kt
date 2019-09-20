package com.rudtyz.bank.repository.global

import com.rudtyz.bank.model.BankUser
import org.springframework.data.jpa.repository.JpaRepository

interface BakUserRepository: JpaRepository<BankUser, String>