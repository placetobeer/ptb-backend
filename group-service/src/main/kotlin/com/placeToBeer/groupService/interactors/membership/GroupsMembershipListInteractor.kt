package com.placeToBeer.groupService.interactors.membership

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class GroupsMembershipListInteractor(private var groupRepository: GroupRepository, private var membershipRepository: MembershipRepository,
                                     private var groupExistValidatorPlugin: GroupExistValidatorPlugin) {

    fun execute(groupId: Long): List<GroupsMembership> {
        val possibleGroup = groupRepository.findById(groupId)
        val group = groupExistValidatorPlugin.validateAndReturn(possibleGroup, groupId)
        return getGroupsMembershipListByGroup(group)
    }

    private fun getGroupsMembershipListByGroup(group: Group): List<GroupsMembership> {
        val userMemberships = mutableListOf<GroupsMembership>()
        for (membership in getMembershipListByGroup(group)) {
            userMemberships.add(GroupsMembership(membership))
        }
        return userMemberships
    }

    private fun getMembershipListByGroup(group: Group): List<Membership>{
        return membershipRepository.findByGroup(group)
    }

}