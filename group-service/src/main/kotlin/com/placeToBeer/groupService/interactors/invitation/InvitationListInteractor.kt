package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.InvitationResponse
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupNameValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.springframework.stereotype.Component
import java.util.*


@Component
class InvitationListInteractor(
    private var invitationRepository: InvitationRepository,
    private var userRepository: UserRepository,
    private var groupRepository: GroupRepository,
    private var userExistValidatorPlugin: UserExistValidatorPlugin
) {

    fun execute(userId: Long): List<InvitationResponse>{
        val user = getUserById(userId)
        return getInvitationsListByUser(user)
    }

    private fun getInvitationsListByUser(user: User): List<InvitationResponse> {
        val invitationsList: MutableList<InvitationResponse> = mutableListOf()
        for(invitation in invitationRepository.findAll()){
            if(invitation.recipient == user){
                invitationsList.add(InvitationResponse(invitation))
            }
        }
        return invitationsList
    }

    private fun getUserById(userId: Long): User{
        val user = userRepository.findById(userId)
        return userExistValidatorPlugin.validateAndReturn(user, userId)
    }
}