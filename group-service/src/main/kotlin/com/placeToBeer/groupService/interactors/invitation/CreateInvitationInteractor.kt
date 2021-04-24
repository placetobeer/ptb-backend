package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class CreateInvitationInteractor(
    private var userRepository: UserRepository,
    private var groupRepository: GroupRepository,
    private var invitationRepository: InvitationRepository,
    private var userExistValidatorPlugin: UserExistValidatorPlugin,
    private var groupExistValidatorPlugin: GroupExistValidatorPlugin
) {
    fun execute() {
        //TODO
    }

    private fun createNewInvitationByIds(email: String, receiverId: Long, emitterId: Long, groupId: Long, role: Role) {
        val receiver = getUserByUserId(receiverId)
        val emitter = getUserByUserId(emitterId)
        val group = getGroupByGroupId(groupId)
        createNewInvitation(email, receiver, emitter, group, role)
    }

    private fun createNewInvitation(email:String, receiver: User, emitter: User, group: Group, role: Role){
        val newInvitation = Invitation(
            email,
            receiver,
            emitter,
            group,
            role
        )
        invitationRepository.save(newInvitation)
    }

    private fun getUserByUserId(userId:Long):User{
        val user = userRepository.findById(userId)
        return userExistValidatorPlugin.validateAndReturn(user,userId)
    }

    private fun getGroupByGroupId(groupId: Long):Group{
        val group = groupRepository.findById(groupId)
        return groupExistValidatorPlugin.validateAndReturn(group,groupId)
    }
}