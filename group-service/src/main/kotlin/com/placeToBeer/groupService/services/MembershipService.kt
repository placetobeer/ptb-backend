package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.interactors.membership.GroupsMembershipListInteractor
import org.springframework.stereotype.Service

@Service
class MembershipService(private val groupsMembershipListInteractor: GroupsMembershipListInteractor) {

    fun getGroupsMembershipListByGroupId(groupId: Long): List<GroupsMembership> {
        return groupsMembershipListInteractor.execute(groupId)
    }

}