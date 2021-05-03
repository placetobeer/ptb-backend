package com.placeToBeer.groupService.entities.requests

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.gateways.GroupRepository

class InvitationRequest(val groupId: Long, val emitter: User, val invitationList: List<InvitationItem>) {


}

