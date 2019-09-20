package com.rudtyz.bank.service

import com.rudtyz.bank.exception.LoginFailIdException
import com.rudtyz.bank.exception.LoginFailPasswordException
import com.rudtyz.bank.exception.UserIdExistsException
import com.rudtyz.bank.model.BankUser
import com.rudtyz.bank.repository.global.BakUserRepository
import com.rudtyz.bank.util.encodeSha512
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class UserSignService(
        private val bankUserRepository: BakUserRepository,
        private val jwtService: JwtService
) {
    @Transactional(isolation=Isolation.SERIALIZABLE)
    fun signUp(id: String, pw: String): String {
        if (bankUserRepository.existsById(id)) {
            throw UserIdExistsException(id)
        }
        bankUserRepository.save(BankUser(id, encodeSha512(pw)))
        return jwtService.encode("perm" to BankUser.PERMISSION_ADMIN)
    }

    @Transactional(isolation=Isolation.SERIALIZABLE)
    fun signIn(id: String, pw: String): String {
        val mayBeUser = bankUserRepository.findById(id)
        val user = mayBeUser.orElseGet { throw LoginFailIdException(id) }

        if (user.hashPassword != encodeSha512(pw)) {
            throw LoginFailPasswordException(id)
        }

        return jwtService.encode("perm" to BankUser.PERMISSION_ADMIN)
    }

    fun refresh(auth: String) = jwtService.encode("perm" to BankUser.PERMISSION_ADMIN)
}