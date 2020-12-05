package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.responses.UserMembership
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MembershipService(private val groupRepository: GroupRepository, private val membershipRepository: MembershipRepository) {

    private var logger: Logger = LoggerFactory.getLogger(GroupService::class.java)

    fun getMembershipList(groupId: Long): List<UserMembership> {
        val userMemberships = mutableListOf<UserMembership>()
        val group = groupRepository.findById(groupId)
        if (group.isEmpty) {
            logger.error("No group with groupId $groupId found")
            return emptyList()
        }
        val memberships = membershipRepository.findByGroup(group.get())
        for (membership in memberships) {
            userMemberships.add(UserMembership(membership))
        }

        return userMemberships
    }
}