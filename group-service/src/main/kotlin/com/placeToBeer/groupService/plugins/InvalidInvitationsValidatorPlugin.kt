package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.exceptions.InvalidInvitationsException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class InvalidInvitationsValidatorPlugin (
    private var emailFormatValidatorPlugin: EmailFormatValidatorPlugin
    ) {

    private var logger: Logger = LoggerFactory.getLogger(InvalidInvitationsValidatorPlugin::class.java)

    fun validate(invitation: Invitation) {
        val invalidInvitations: MutableList<Invitation> = mutableListOf()
        runCatching { emailFormatValidatorPlugin.validate(invitation.email) }.onFailure {
            invalidInvitations.add(invitation)
        }
        if (invalidInvitations.isNotEmpty()) {
            throwInvalidInvitationsError(invalidInvitations)
        }
    }

    private fun throwInvalidInvitationsError(invalidInvitations: MutableList<Invitation>) {
        logger.error("Could not create the list of invitations $invalidInvitations because of invalid values.")
        throw InvalidInvitationsException(invalidInvitations)
    }
}