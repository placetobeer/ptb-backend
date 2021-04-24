package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.interactors.group.CreateGroupInteractor
import com.placeToBeer.groupService.interactors.group.DeleteGroupInteractor
import com.placeToBeer.groupService.interactors.group.GroupListInteractor
import com.placeToBeer.groupService.interactors.group.SetGroupNameInteractor
import org.springframework.stereotype.Service

@Service
class GroupService (private var groupListInteractor: GroupListInteractor, private var createGroupInteractor: CreateGroupInteractor,
                    private var setGroupNameInteractor: SetGroupNameInteractor, private var deleteGroupInteractor: DeleteGroupInteractor){

    fun getGroupListByUserId(userId: Long): List<Group>{
       return groupListInteractor.execute(userId)
    }

    fun createGroup(userId: Long, groupName: String): Group {
       return createGroupInteractor.execute(userId, groupName)
    }

    fun setGroupNameByGroupId(groupId: Long, groupName: String) {
        setGroupNameInteractor.execute(groupId, groupName)
    }

    fun deleteGroup(groupId: Long) {
        deleteGroupInteractor.execute(groupId)
    }

}