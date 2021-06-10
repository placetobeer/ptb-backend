package com.placeToBeer.groupService.interactors.membership

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.MembershipExistValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class GroupsMembershipInteractor(private var userRepository: UserRepository, private var userExistValidatorPlugin: UserExistValidatorPlugin,
        private var membershipRepository: MembershipRepository, private var groupRepository: GroupRepository,
        private var groupExistValidatorPlugin: GroupExistValidatorPlugin, private var membershipExistValidatorPlugin: MembershipExistValidatorPlugin) {

    fun execute(userId: Long, groupId: Long): GroupsMembership {
        val possibleUser = userRepository.findById(userId)
        val user = userExistValidatorPlugin.validateAndReturn(possibleUser, userId)
        val possibleGroup = groupRepository.findById(groupId)
        val group = groupExistValidatorPlugin.validateAndReturn(possibleGroup, groupId)
        return getGroupsMembershipByUserAndGroup(user, group)
    }

    private fun getGroupsMembershipByUserAndGroup(user: User, group: Group): GroupsMembership {
        val possibleMembership = membershipRepository.findByMemberAndGroup(user, group);
        val membership = membershipExistValidatorPlugin.validateAndReturn(possibleMembership, possibleMembership.get().id!!)
        return GroupsMembership(membership)
    }
}