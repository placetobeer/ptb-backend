package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.interactors.CreateGroupInteractor
import com.placeToBeer.groupService.interactors.GroupListInteractor
import com.placeToBeer.groupService.interactors.SetGroupNameInteractor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GroupService (private var groupListInteractor: GroupListInteractor, private var createGroupInteractor: CreateGroupInteractor,
                    private var setGroupNameInteractor: SetGroupNameInteractor){

    fun getGroupListByUserId(userId: Long): List<Group>{
       return groupListInteractor.execute(userId)
    }

    fun createGroup(userId: Long, groupName: String): Group {
       return createGroupInteractor.execute(userId, groupName)
    }

    fun setGroupNameByGroupId(groupId: Long, groupName: String) {
        setGroupNameInteractor.execute(groupId, groupName);
    }

}