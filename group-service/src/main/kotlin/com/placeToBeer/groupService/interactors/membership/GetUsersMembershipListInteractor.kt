package com.placeToBeer.groupService.interactors.membership

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.responses.UsersMembership
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class GetUsersMembershipListInteractor(private var groupRepository: GroupRepository, private var membershipRepository: MembershipRepository,
                                       private var groupExistValidatorPlugin: GroupExistValidatorPlugin) {

    fun execute(groupId: Long): List<UsersMembership> {
        val possibleGroup = groupRepository.findById(groupId)
        val group = groupExistValidatorPlugin.validateAndReturn(possibleGroup, groupId)
        return getUserMembershipListByGroup(group)
    }

    private fun getUserMembershipListByGroup(group: Group): List<UsersMembership> {
        val userMemberships = mutableListOf<UsersMembership>()
        for (membership in getMembershipListByGroup(group)) {
            userMemberships.add(UsersMembership(membership))
        }
        return userMemberships
    }

    private fun getMembershipListByGroup(group: Group): List<Membership>{
        return membershipRepository.findByGroup(group)
    }

}