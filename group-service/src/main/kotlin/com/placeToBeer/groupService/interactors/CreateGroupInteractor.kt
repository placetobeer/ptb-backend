package com.placeToBeer.groupService.interactors

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupNameValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class CreateGroupInteractor(private var groupRepository: GroupRepository, private var userRepository: UserRepository,
                            private var membershipRepository: MembershipRepository, private var groupNameValidatorPlugin: GroupNameValidatorPlugin,
                            private var userExistValidatorPlugin: UserExistValidatorPlugin) {

    fun execute(userId: Long, groupName: String): Group {
        val newGroup = createGroup(groupName)
        setOwnership(newGroup, userId)
        return newGroup
    }

    private fun createGroup(groupName: String): Group{
        groupNameValidatorPlugin.validate(groupName);
        val newGroup = Group(groupName)
        return groupRepository.save(newGroup);
    }

    private fun setOwnership(newGroup: Group, userId: Long) {
        val user = getUserByUserId(userId)
        createOwnership(user, newGroup)
    }

    private fun getUserByUserId(userId: Long): User {
        val user = userRepository.findById(userId)
        return userExistValidatorPlugin.validateAndReturn(user, userId)
    }

    private fun createOwnership(user: User, group: Group){
        val ownership = Membership(group, user, Role.OWNER)
        membershipRepository.save(ownership)
    }



}