package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserRegisteredValidatorPlugin{

    private var logger: Logger = LoggerFactory.getLogger(UserExistValidatorPlugin::class.java)

    fun validateAndReturn(user: Optional<User>, userEmail: String): User {
        if(user.isEmpty){
            throwUserNotFoundError(userEmail)
        }
        return user.get()
    }

    private fun throwUserNotFoundError(email: String) {
        try {
            throw UserNotFoundException(0L)
        } catch (e: UserNotFoundException){
            logger.warn("user with email $email not registered")
        }
    }
}