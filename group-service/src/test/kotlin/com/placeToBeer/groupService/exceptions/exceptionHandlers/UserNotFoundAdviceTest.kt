package com.placeToBeer.groupService.exceptions.exceptionHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import org.assertj.core.api.Assertions

import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

internal class UserNotFoundAdviceTest {

    private val userNotFoundAdvice = UserNotFoundAdvice()

    @Test
    fun whenEmployeeNotFoundHandlerIsInvokedWithException_ThenReturnMessageStringAsJson() {
        val exception = UserNotFoundException(1)
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json; charset=utf-8")
        val exceptionMessageJson = ObjectMapper().writeValueAsString(exception.message)
        val responseEntity = ResponseEntity(exceptionMessageJson, headers, HttpStatus.NOT_FOUND)
        Assertions.assertThat(userNotFoundAdvice.employeeNotFoundHandler(exception)).isEqualTo(responseEntity)
    }
}