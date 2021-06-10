package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.InvitationNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*
import java.util.*

class InvitationExistsValidatorPluginTest {

    private val invitationExistsValidatorPlugin = InvitationExistsValidatorPlugin()

    private var exception: Exception? = null

    private val validInvitationId = 1L
    private val validInvitation = Invitation(
        validInvitationId,
        "receiver@mail.com",
        User(1L, "Receiver", "receiver@mail.com"),
        User(2L, "Emitter", "emitter@mail.com"),
        Group(1L, "groupName"),
        Role.MEMBER)

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun validateAndReturnWithExistingInvitation_ThenReturnInvitation() {
        val invitation = doValidateAndReturn(Optional.of(validInvitation), validInvitationId)
        assertThat(exception).isNull()
        assertThat(invitation).isEqualTo(validInvitation)
    }

    @Test
    fun validateAndReturnWithEmptyInvitation_ThenThrowInvitationNotFoundError() {
        val invitation = doValidateAndReturn(Optional.empty(), validInvitationId)
        assertThat(exception).isInstanceOf(InvitationNotFoundException::class.java)
        assertThat(invitation).isNull()
    }

    private fun doValidateAndReturn(invitation: Optional<Invitation>, invitationId: Long): Invitation? {
        var realInvitation: Invitation? = null
        try {
            realInvitation = invitationExistsValidatorPlugin.validateAndReturn(invitation, invitationId)
        } catch (exception: java.lang.Exception){
            this.exception = exception
        }
        return realInvitation
    }
}