package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.*
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.InvitationExistsValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class AnswerInvitationInteractor(
    private var invitationRepository: InvitationRepository,
    private var membershipRepository: MembershipRepository,
    private var userRepository: UserRepository,
    private var groupRepository: GroupRepository,
    private var invitationExistsValidatorPlugin: InvitationExistsValidatorPlugin,
    private var userExistValidatorPlugin: UserExistValidatorPlugin,
    private var groupExistValidatorPlugin: GroupExistValidatorPlugin
) {
    fun execute(invitationId:Long, decision: Boolean){
        val invitation = getInvitationById(invitationId)
        if (decision){
            val groupId = invitation.group.id ?: 0
            val userId = invitation.recipient.id ?: 0
            createMembershipByIds(groupId, userId, invitation.role)
            //delete invitation before creating Membership?
        }
        deleteInvitation(invitation)
    }

    private fun createMembershipByIds( groupId: Long,userId: Long, role: Role){
        val group = getGroupByGroupId(groupId)
        val user = getUserByUserId(userId)
        createMembership(group, user, role)
    }

    private fun createMembership(group: Group, user: User, role: Role){
        val newMembership = Membership(
            group,
            user,
            role
        )
        membershipRepository.save(newMembership)
    }

    private fun getUserByUserId(userId:Long): User {
        val user = userRepository.findById(userId)
        return userExistValidatorPlugin.validateAndReturn(user,userId)
    }

    private fun getGroupByGroupId(groupId: Long): Group {
        val group = groupRepository.findById(groupId)
        return groupExistValidatorPlugin.validateAndReturn(group,groupId)
    }

    private fun deleteInvitation(invitation: Invitation){
        invitationRepository.delete(invitation)
    }

    private fun getInvitationById(invitationId: Long): Invitation{
        val invitation = invitationRepository.findById(invitationId)
        return invitationExistsValidatorPlugin.validateAndReturn(invitation, invitationId)
    }
}