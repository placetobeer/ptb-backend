package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserRegisteredValidatorPlugin {

    public fun validateAndReturn(user: User?, userEmail: String): User?{
        return user
    }
}