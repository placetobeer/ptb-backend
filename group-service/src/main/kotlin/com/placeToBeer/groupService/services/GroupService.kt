package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupService (private var membershipRepository: MembershipRepository, private var userRepository: UserRepository,
                    private var groupRepository: GroupRepository){

    private var logger: Logger = LoggerFactory.getLogger(GroupService::class.java)

    fun getGroupListByUserId(userId: Long): List<Group>{
        checkIfUserIsEmpty(userId)
        return getGroupListByUser(userRepository.findById(userId).get())
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

    fun createGroup(userId: Long, groupName: String): Group {
        var newGroup = Group(groupName)
        newGroup = groupRepository.save(newGroup)
        checkIfUserIsEmpty(userId)
        checkIfGroupIsEmpty(newGroup)
        this.createOwnership(userId, newGroup)
        return newGroup
    }

    private fun createOwnership(userId: Long, group: Group){
        val ownership = Membership(group, userRepository.findById(userId).get(), Role.OWNER)
        membershipRepository.save(ownership)
    }

    private fun checkIfUserIsEmpty(userId: Long){
        val user = userRepository.findById(userId)
        if(user.isEmpty){
            throwUserNotFoundError(userId)
        }
    }

    private fun checkIfGroupIsEmpty(group: Group){
        if(Optional.of(group).isEmpty){
            throwGroupNotFoundError(group.id!!)
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