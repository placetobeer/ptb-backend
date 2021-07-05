package com.placeToBeer.groupService.exceptions

import com.placeToBeer.groupService.entities.Invitation
import org.springframework.stereotype.Component

@Component
class InvalidInvitationsException(invalidInvitations: MutableList<Invitation>):
    AbstractInvalidListException("invitations", invalidInvitations)
{}