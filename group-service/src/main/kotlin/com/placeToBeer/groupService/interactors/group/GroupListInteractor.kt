package com.placeToBeer.groupService.interactors.group

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupNameValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class GroupListInteractor(private var membershipRepository: MembershipRepository, private var userRepository: UserRepository,
                          private var userExistValidatorPlugin: UserExistValidatorPlugin) {

    fun execute(userId: Long): List<Group>{
        val user = getUserByUserId(userId)
        return getGroupListByUser(user)
    }

    private fun getGroupListByUser(user: User): List<Group> {
        val groupList: MutableList<Group> = mutableListOf()
        for(membership in getMembershipsByUser(user)){
            groupList.add(membership.group)
        }
        return groupList
    }

    private fun getMembershipsByUser(user: User): List<Membership> {
        return membershipRepository.findByMember(user)
    }

    private fun getUserByUserId(userId: Long): User{
        val user = userRepository.findById(userId)
        return userExistValidatorPlugin.validateAndReturn(user, userId)
    }


}