package com.rudtyz.bank.repository.global.logger

import com.rudtyz.bank.model.logger.ProcessFail
import org.springframework.data.jpa.repository.JpaRepository

interface ProcessFailRepository: JpaRepository<ProcessFail, Int>
