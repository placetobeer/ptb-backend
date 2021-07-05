package com.placeToBeer.groupService.entities.responses

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role

data class GroupInvitation(
        val id: Long,
        val mail: String,
        val recipientName: String?,
        val role: Role) {

    constructor(invitation: Invitation): this(invitation.id!!, invitation.email, invitation.recipient?.name, invitation.role)
}
