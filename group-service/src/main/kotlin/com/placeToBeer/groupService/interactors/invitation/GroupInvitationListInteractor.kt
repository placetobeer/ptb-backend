package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.responses.GroupInvitation
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GroupInvitationListInteractor(private val invitationRepository: InvitationRepository,
                                    private val groupRepository: GroupRepository) {

    private var logger: Logger = LoggerFactory.getLogger(GroupInvitationListInteractor::class.java)

    fun execute(groupId: Long): List<GroupInvitation> {
        checkForExistingGroup(groupId)
        return getGroupInvitationList(groupId)
    }

    private fun getGroupInvitationList(groupId: Long): List<GroupInvitation> {
        val invitations = invitationRepository.findAllByGroupId(groupId)
        val groupInvitationList: MutableList<GroupInvitation> = mutableListOf()
        invitations.forEach { invitation -> groupInvitationList.add(GroupInvitation(invitation)) }
        return groupInvitationList;
    }

    private fun checkForExistingGroup(groupId: Long) {
        if(!groupRepository.existsById(groupId)) {
            logger.error("No group with groupId $groupId found")
            throw GroupNotFoundException(groupId)
        }
    }
}