package com.placeToBeer.groupService.exceptions.exceptionHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.InvalidInvitationsException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

internal class InvalidListAdviceTest {

    private val invalidListAdvice = InvalidListAdvice()

    private val validGroup = Group(1, "Bratis Kartoffeln")
    private val validEmitter = User(4, "Patrick")
    private val invalidEmail = "iam@wrong"
    private val invalidInvitation = Invitation( invalidEmail, validEmitter, validGroup, Role.ADMIN)

    @Test
    fun whenInvalidListHandlerIsInvokedWithException_ThenReturnMessageStringAsJson() {
        val invalidInvitations: MutableList<Invitation> = mutableListOf(invalidInvitation)
        val exception = InvalidInvitationsException(invalidInvitations)
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json; charset=utf-8")
        val exceptionMessageJson = ObjectMapper().writeValueAsString(exception.message)
        val responseEntity = ResponseEntity(exceptionMessageJson, headers, HttpStatus.UNPROCESSABLE_ENTITY)
        Assertions.assertThat(invalidListAdvice.invalidValueHandler(exception)).isEqualTo(responseEntity)
    }
}