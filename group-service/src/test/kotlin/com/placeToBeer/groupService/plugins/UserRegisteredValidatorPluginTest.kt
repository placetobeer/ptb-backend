package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class UserRegisteredValidatorPluginTest {
    private val userRegisteredValidatorPlugin = UserRegisteredValidatorPlugin()

    private var exception: Exception? = null

    private val validUserMail = "user@mail.com"
    private val invalidUserMail = "no-user@mail.com"
    private val validUser = User("userName", validUserMail)

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun validateAndReturnWithExistingUser_ThenReturnUser(){
        val user = doValidateAndReturn(Optional.of(validUser), validUserMail)
        assertThat(exception).isNull()
        assertThat(user).isEqualTo(validUser)
    }

    @Test
    fun validateAndReturnWithUnregisteredUser_ThenReturnUserNotFoundError(){
        val user = doValidateAndReturn(Optional.empty(),invalidUserMail)
        assertThat(exception).isInstanceOf(UserNotFoundException::class.java)
        assertThat(user).isNull()
    }

    private fun doValidateAndReturn(user: Optional<User>, userEmail: String):User?{
        var isUser: User? = null
        try {
            isUser = userRegisteredValidatorPlugin.validateAndReturn(user,userEmail)
        } catch (exception: UserNotFoundException){
            this.exception = exception
        }
        return isUser
    }
}