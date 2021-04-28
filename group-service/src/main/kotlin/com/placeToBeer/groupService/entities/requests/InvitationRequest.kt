package com.placeToBeer.groupService.entities.requests

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.requests.InvitationObject
import com.placeToBeer.groupService.entities.responses.InvitationResponse
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.UserRepository

class InvitationRequest(val groupId: Long, private val emitter: User, private val invitationList: List<InvitationObject>, val groupRepository: GroupRepository) {

    fun mapToInvitationEntity(): List<Invitation> {
        val invitationEntityList: MutableList<Invitation> = mutableListOf()
        for ( invitation in invitationList ) {
            var role = Role.MEMBER
            if (invitation.grantAdmin){
                role = Role.ADMIN
            }
            invitationEntityList.add(Invitation(invitation.email, emitter, groupRepository.findById(groupId).get(), role))
        }
        return invitationEntityList;
    }
}

