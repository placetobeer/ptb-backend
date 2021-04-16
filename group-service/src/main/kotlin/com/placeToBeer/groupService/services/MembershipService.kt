package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.interactors.membership.GetGroupsMembershipListInteractor
import org.springframework.stereotype.Service

@Service
class MembershipService(private val getGroupsMembershipListInteractor: GetGroupsMembershipListInteractor) {

    fun getGroupsMembershipListByGroupId(groupId: Long): List<GroupsMembership> {
        return getGroupsMembershipListInteractor.execute(groupId)
    }

}