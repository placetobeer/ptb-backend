package com.placeToBeer.groupService.entities.responses

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User

class InvitationResponse(var id: Long, val emitter: User, val group: Group, val role: Role){
    constructor(invitation: Invitation): this(

        invitation.id!! ,
        invitation.emitter,
        invitation.group,
        invitation.role
    ){

    }
}