package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.mail.EmailServiceImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserRegisteredValidatorPlugin{
    private var logger: Logger = LoggerFactory.getLogger(UserRegisteredValidatorPlugin::class.java)

    public fun validateAndReturn(userList: List<User>, userEmail: String): User? {
        return if (userList.isEmpty()) {
            logger.warn("no user with email: $userEmail found!")
            null
        } else {
            userList.last()
        }
    }
}