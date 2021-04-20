package com.placeToBeer.groupService.interactors.membership

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class CreateMembershipInteractor(
    private var userRepository: UserRepository,
    private var groupRepository: GroupRepository,
    private var membershipRepository: MembershipRepository,
    private var userExistValidatorPlugin: UserExistValidatorPlugin,
    private var groupExistValidatorPlugin: GroupExistValidatorPlugin
){
    fun execute( groupId: Long,userId: Long, role: Role){
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
}