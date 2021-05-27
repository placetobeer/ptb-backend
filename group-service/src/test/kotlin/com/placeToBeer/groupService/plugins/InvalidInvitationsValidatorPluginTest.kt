package com.placeToBeer.groupService.plugins

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.*
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.exceptions.EmailIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.InvalidInvitationsException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception

internal class InvalidInvitationsValidatorPluginTest
{
    private val emailFormatValidatorPlugin: EmailFormatValidatorPlugin = mock()

    private val invalidInvitationsValidatorPlugin =  InvalidInvitationsValidatorPlugin(emailFormatValidatorPlugin)

    private var exception: Exception? = null

    private val validGroup = Group(1, "Bratis Kartoffeln")
    private val validEmitter = User(4, "Patrick")
    private val validInvitation = Invitation( "a.b@gmail.com", validEmitter, validGroup, Role.ADMIN)

    private val invalidEmail = "iam@invalid"
    private val invalidEmailInvitation = Invitation( invalidEmail, validEmitter, validGroup, Role.ADMIN)

    @BeforeEach
    fun setUp(){
        exception = null
    }

    init {
        whenever(emailFormatValidatorPlugin.validate(invalidEmail)).thenThrow(EmailIsInvalidException::class.java)
    }

    @Test
    fun validateWithValidInvitation_ThenDoNothing() {
        doValidate(validInvitation)
        Assertions.assertThat(exception).isNull()
    }

    @Test
    fun validateWithInvalidEmail_ThenThrowInvalidInvitationsError() {
        doValidate(invalidEmailInvitation)
        Assertions.assertThat(this.exception).isExactlyInstanceOf(InvalidInvitationsException::class.java)
    }

    private fun doValidate(invitation: Invitation){
        try {
            invalidInvitationsValidatorPlugin.validate(invitation)
        } catch (exception: Exception){
            this.exception = exception
        }
    }
}