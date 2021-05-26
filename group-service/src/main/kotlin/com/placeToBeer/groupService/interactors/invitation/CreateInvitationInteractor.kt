package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.plugins.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateInvitationInteractor(
    private var groupRepository: GroupRepository,
    private var invitationRepository: InvitationRepository,
    private var membershipRepository: MembershipRepository,
    private var groupExistValidatorPlugin: GroupExistValidatorPlugin,
    private var userExistValidatorPlugin: UserExistValidatorPlugin,
    private var membershipExistValidatorPlugin: MembershipExistValidatorPlugin,
    private var invalidInvitationsValidatorPlugin: InvalidInvitationsValidatorPlugin
) {
    fun execute(invitationRequest: InvitationRequest): List<Invitation> {
        return createInvitations(invitationRequest)
    }

    private fun createInvitations(invitationRequest: InvitationRequest): List<Invitation> {
        val savedInvitations: MutableList<Invitation> = mutableListOf()
        validateEmitterAndGroup(invitationRequest.emitter, getGroupByGroupId(invitationRequest.groupId))
        for (invitation in mapToInvitationEntity(invitationRequest)) {
            invalidInvitationsValidatorPlugin.validate(invitation)
            savedInvitations.add(invitationRepository.save(invitation))
        }
        return savedInvitations
    }

    private fun validateEmitterAndGroup(emitter: User, group: Group){
        userExistValidatorPlugin.validateAndReturn(Optional.of(emitter), emitter.id!!)
        groupExistValidatorPlugin.validateAndReturn(Optional.of(group), group.id!!)
        val possibleMembership = membershipRepository.findByMemberAndGroup(emitter, group)
        membershipExistValidatorPlugin.validateAndReturn(possibleMembership, possibleMembership.get().id!!)
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
