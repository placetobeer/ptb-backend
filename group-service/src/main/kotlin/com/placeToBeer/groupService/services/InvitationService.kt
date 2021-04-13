package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.responses.InvitationResponse
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class InvitationService(
    private var invitationRepository: InvitationRepository,
    private var userRepository: UserRepository,
    private var groupRepository: GroupRepository
    ) {

    private var logger: Logger = LoggerFactory.getLogger(InvitationService::class.java)

    fun getInvitationsListByUserId(userId: Long): List<InvitationResponse>{
        checkIfUserIsEmpty(userId)
        return getInvitationsListByUser(userRepository.findById(userId).get())
    }

    private fun getInvitationsListByUser(user: User): MutableList<InvitationResponse> {
        val invitationsList: MutableList<InvitationResponse> = mutableListOf()
        for(invitation in invitationRepository.findAll()){
            if(invitation.recipient == user){
                invitationsList.add(InvitationResponse(invitation))
            }
        }
        return invitationsList
    }

    fun createNewInvitation(receiverId: Long, emitterId: Long, groupId: Long, role: Role){
        checkIfUserIsEmpty(receiverId)
        checkIfUserIsEmpty(emitterId)
        checkIfGroupIsEmpty(groupId)
        val newInvitation = Invitation(
            userRepository.findById(receiverId).get(),
            userRepository.findById(emitterId).get(),
            groupRepository.findById(groupId).get(),
            role
        )
        invitationRepository.save(newInvitation)
    }

    private fun checkIfUserIsEmpty(userId: Long){
        val user = userRepository.findById(userId)
        if(user.isEmpty){
            throwUserNotFoundError(userId)
        }
    }

    private fun checkIfGroupIsEmpty(groupId: Long){
        val group = groupRepository.findById(groupId)
        if(Optional.of(group).isEmpty){
            throwGroupNotFoundError(groupId)
        }
    }

    private fun throwUserNotFoundError(userId: Long) {
        logger.error("No user with userId $userId found")
        throw UserNotFoundException(userId)
    }

    private fun throwGroupNotFoundError(groupId: Long) {
        logger.error("No group with groupId $groupId found")
        throw GroupNotFoundException(groupId)
    }

}