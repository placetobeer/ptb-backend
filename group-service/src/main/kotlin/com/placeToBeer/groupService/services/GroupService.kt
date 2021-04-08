package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GroupService (private var membershipRepository: MembershipRepository, private var userRepository: UserRepository,
                    private var groupRepository: GroupRepository){

    private var logger: Logger = LoggerFactory.getLogger(GroupService::class.java)

    fun getGroupListByUserId(userId: Long): List<Group>{
        val user = getUserByUserId(userId)
        return getGroupListByUser(user)
    }

    fun createGroup(userId: Long, groupName: String): Group {
        checkIfGroupNameIsValid(groupName)
        val newGroup = Group(groupName)
        groupRepository.save(newGroup)
        val user = getUserByUserId(userId)
        createOwnership(user, newGroup)
        return newGroup
    }

    fun setGroupNameByGroupId(groupId: Long, groupName: String) {
        checkIfGroupNameIsValid(groupName)
        val group = getGroupByGroupId(groupId)
        group.name = groupName
        groupRepository.save(group)
    }

    private fun checkIfGroupNameIsValid(groupName: String) {
        if(groupName.isNullOrEmpty()){
            throwGroupNameIsInvalidError(groupName)
        }
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

    fun createOwnership(user: User, group: Group){
        val ownership = Membership(group, user, Role.OWNER)
        membershipRepository.save(ownership)
    }

    private fun getUserByUserId(userId: Long): User{
        val user = userRepository.findById(userId)
        if(user.isEmpty){
            throwUserNotFoundError(userId)
        }
        return user.get()
    }

    private fun getGroupByGroupId(groupId: Long): Group {
        val possibleGroup = groupRepository.findById(groupId)
        if(possibleGroup.isEmpty){
            throwGroupNotFoundError(groupId)
        }
        return possibleGroup.get()
    }

    private fun throwUserNotFoundError(userId: Long) {
        logger.error("No user with userId $userId found")
        throw UserNotFoundException(userId)
    }

    private fun throwGroupNotFoundError(groupId: Long) {
        logger.error("No group with groupId $groupId found")
        throw GroupNotFoundException(groupId)
    }

    private fun throwGroupNameIsInvalidError(groupName: String) {
        logger.error("Group name $groupName is invalid")
        throw GroupNameIsInvalidException(groupName)
    }

}