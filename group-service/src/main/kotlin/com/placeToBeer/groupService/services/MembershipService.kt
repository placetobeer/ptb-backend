package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.interactors.membership.DeleteMembershipInteractor
import com.placeToBeer.groupService.interactors.membership.GroupsMembershipListInteractor
import org.springframework.stereotype.Service

@Service
class MembershipService(private val groupsMembershipListInteractor: GroupsMembershipListInteractor, private val deleteMembershipInteractor: DeleteMembershipInteractor) {

    fun getGroupsMembershipListByGroupId(groupId: Long): List<GroupsMembership> {
        return groupsMembershipListInteractor.execute(groupId)
    }

    fun deleteMembershipById(membershipId: Long) {
        deleteMembershipInteractor.execute(membershipId)
    }
}
