package com.placeToBeer.groupService.exceptions.exceptionHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import org.assertj.core.api.Assertions

import org.junit.jupiter.api.Test

internal class UserNotFoundAdviceTest {

    private val userNotFoundAdvice = UserNotFoundAdvice()

    @Test
    fun whenEmployeeNotFoundHandlerIsInvokedWithException_ThenReturnMessageStringAsJson() {
        val exception = UserNotFoundException(1)
        val expectedJson = ObjectMapper().writeValueAsString(exception.message)
        Assertions.assertThat(userNotFoundAdvice.employeeNotFoundHandler(exception)).isEqualTo(expectedJson)
    }
}