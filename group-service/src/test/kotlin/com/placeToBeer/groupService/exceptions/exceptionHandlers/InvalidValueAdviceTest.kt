package com.placeToBeer.groupService.exceptions.exceptionHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import org.assertj.core.api.Assertions

import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

internal class InvalidValueAdviceTest {

    private val invalidValueAdvice = InvalidValueAdvice()

    @Test
    fun whenInvalidValueHandlerIsInvokedWithException_ThenReturnMessageStringAsJson() {
        val invalidGroupName = "invalidGroupName"
        val exception = GroupNameIsInvalidException(invalidGroupName)
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json; charset=utf-8")
        val exceptionMessageJson = ObjectMapper().writeValueAsString(exception.message)
        val responseEntity = ResponseEntity(exceptionMessageJson, headers, HttpStatus.UNPROCESSABLE_ENTITY)
        Assertions.assertThat(invalidValueAdvice.invalidValueHandler(exception)).isEqualTo(responseEntity)
    }
}