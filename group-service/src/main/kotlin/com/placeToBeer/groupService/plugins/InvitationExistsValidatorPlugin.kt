package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.exceptions.InvitationNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class InvitationExistsValidatorPlugin {

    private var logger: Logger = LoggerFactory.getLogger(InvitationExistsValidatorPlugin::class.java)

    fun validateAndReturn(invitation: Optional<Invitation>, invitationId: Long):Invitation {
        if(invitation.isEmpty){
            throwInvitationNotFoundError(invitationId)
        }
        return invitation.get()
    }

    private fun throwInvitationNotFoundError(invitationId: Long) {
        logger.error("No invitation with invitationId $invitationId found")
        throw InvitationNotFoundException(invitationId)
    }
}