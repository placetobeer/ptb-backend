package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.interactors.membership.CreateMembershipInteractor
import com.placeToBeer.groupService.interactors.membership.GetGroupsMembershipListInteractor
import org.springframework.stereotype.Service

@Service
class MembershipService(
    private val getGroupsMembershipListInteractor: GetGroupsMembershipListInteractor
    //, private val createMembershipInteractor: CreateMembershipInteractor
    ) {

    fun getGroupsMembershipListByGroupId(groupId: Long): List<GroupsMembership> {
        return getGroupsMembershipListInteractor.execute(groupId)
    }

    /*
    fun createMembershipByUserId(groupId: Long, userId: Long, role: Role){
        return createMembershipInteractor.execute(groupId, userId, role)
    }*/

}