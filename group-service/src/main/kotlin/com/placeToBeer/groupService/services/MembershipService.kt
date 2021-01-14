package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.responses.UserMembership
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MembershipService(private val groupRepository: GroupRepository, private val membershipRepository: MembershipRepository) {

    private var logger: Logger = LoggerFactory.getLogger(GroupService::class.java)

    fun getUserMembershipListByGroupId(groupId: Long): List<UserMembership> {
        val group = groupRepository.findById(groupId)
        if (group.isEmpty) {
            throwGroupNotFoundError(groupId)
        }
        return getUserMembershipListByGroup(group.get())
    }

    private fun getUserMembershipListByGroup(group: Group): List<UserMembership> {
        val userMemberships = mutableListOf<UserMembership>()
        for (membership in getMembershipListByGroup(group)) {
            userMemberships.add(UserMembership(membership))
        }
        return userMemberships
    }

    private fun getMembershipListByGroup(group: Group): List<Membership>{
        return membershipRepository.findByGroup(group)
    }

    private fun throwGroupNotFoundError(groupId: Long) {
        logger.error("No group with groupId $groupId found")
        throw GroupNotFoundException(groupId)
    }
}