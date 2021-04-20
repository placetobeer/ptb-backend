package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.interactors.membership.CreateMembershipInteractor
import com.placeToBeer.groupService.plugins.InvitationExistsValidatorPlugin
import org.springframework.stereotype.Component

@Component
class AnswerInvitationInteractor(
    private var invitationRepository: InvitationRepository,
    private var invitationExistsValidatorPlugin: InvitationExistsValidatorPlugin,
    private var createMembershipInteractor: CreateMembershipInteractor
) {
    fun execute(invitationId:Long, decision: Boolean){
        val invitation = getInvitationById(invitationId)
        if (decision){
            val groupId = invitation.group.id ?: 0
            val userId = invitation.recipient.id ?: 0
            createMembershipInteractor.execute(groupId, userId, invitation.role)
            //delete invitation before creating Membership?
        }
        deleteInvitation(invitation)
    }

    private fun deleteInvitation(invitation: Invitation){
        invitationRepository.delete(invitation)
    }

    private fun getInvitationById(invitationId: Long): Invitation{
        val invitation = invitationRepository.findById(invitationId)
        return invitationExistsValidatorPlugin.validateAndReturn(invitation, invitationId)
    }
}