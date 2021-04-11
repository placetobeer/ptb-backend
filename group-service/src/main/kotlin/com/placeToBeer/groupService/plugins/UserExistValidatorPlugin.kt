package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.services.GroupService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserExistValidatorPlugin {

    private var logger: Logger = LoggerFactory.getLogger(UserExistValidatorPlugin::class.java)

    fun validateAndReturn(user: Optional<User>, userId: Long): User {
        if(user.isEmpty){
            throwUserNotFoundError(userId)
        }
        return user.get()
    }

    private fun throwUserNotFoundError(userId: Long) {
        logger.error("No user with userId $userId found")
        throw UserNotFoundException(userId)
    }

}