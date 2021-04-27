package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class UserExistValidatorPluginTest{

    private val userExistValidatorPlugin = UserExistValidatorPlugin()

    private var exception: Exception? = null

    private val validUserId = 1L
    private val validUser = User(validUserId, "userName")

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun validateAndReturnWithExistingUser_ThenReturnUser() {
        val user = doValidateAndReturn(Optional.of(validUser), validUserId)
        Assertions.assertThat(exception).isNull()
        Assertions.assertThat(user).isEqualTo(validUser)
    }

    @Test
    fun validateAndReturnWithEmptyUser_ThenThrowUserNotFoundException() {
        val user = doValidateAndReturn(Optional.empty(), validUserId)
        Assertions.assertThat(exception).isInstanceOf(UserNotFoundException::class.java)
        Assertions.assertThat(user).isNull()
    }

    private fun doValidateAndReturn(user: Optional<User>, userId: Long): User? {
        var realUser: User? = null
        try {
            realUser = userExistValidatorPlugin.validateAndReturn(user, userId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return realUser
    }
}