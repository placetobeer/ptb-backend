package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.plugins.*
import org.springframework.stereotype.Component

@Component
class CreateInvitationInteractor(
    private var groupRepository: GroupRepository,
    private var invitationRepository: InvitationRepository,
    private var groupExistValidatorPlugin: GroupExistValidatorPlugin,
    private var invalidInvitationsValidatorPlugin: InvalidInvitationsValidatorPlugin
) {
    fun execute(invitationRequest: InvitationRequest): List<Invitation> {
        return createInvitations(invitationRequest)
    }

    private fun createInvitations(invitationRequest: InvitationRequest): List<Invitation> {
        val savedInvitations: MutableList<Invitation> = mutableListOf()
        for (invitation in mapToInvitationEntity(invitationRequest)) {
            invalidInvitationsValidatorPlugin.validate(invitation)
            savedInvitations.add(invitationRepository.save(invitation))
        }
        return savedInvitations
    }

    private fun mapToInvitationEntity(invitationRequest: InvitationRequest): List<Invitation> {
        val invitationEntityList: MutableList<Invitation> = mutableListOf()
        for ( invitation in invitationRequest.invitationList ) {
            var role = Role.MEMBER
            if (invitation.grantAdmin){
                role = Role.ADMIN
            }
            invitationEntityList.add(Invitation(invitation.email, invitationRequest.emitter, getGroupByGroupId(invitationRequest.groupId), role))
        }
        return invitationEntityList;
    }

    private fun getGroupByGroupId(groupId: Long): Group {
            val group = groupRepository.findById(groupId)
            return groupExistValidatorPlugin.validateAndReturn(group, groupId)
    }
}
