package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.interactors.membership.DeleteMembershipInteractor
import com.placeToBeer.groupService.interactors.membership.GroupsMembershipInteractor
import com.placeToBeer.groupService.interactors.membership.GroupsMembershipListInteractor
import com.placeToBeer.groupService.interactors.membership.SetRoleInteractor
import org.springframework.stereotype.Service

@Service
class MembershipService(private val groupsMembershipListInteractor: GroupsMembershipListInteractor, private val deleteMembershipInteractor: DeleteMembershipInteractor,
                        private val setRoleInteractor: SetRoleInteractor, private val groupsMembershipInteractor: GroupsMembershipInteractor) {

    fun getGroupsMembershipListByGroupId(groupId: Long): List<GroupsMembership> {
        return groupsMembershipListInteractor.execute(groupId)
    }

    fun deleteMembershipById(membershipId: Long) {
        deleteMembershipInteractor.execute(membershipId)
    }

    fun setRole(membershipId: Long, role: Role) {
        setRoleInteractor.execute(membershipId, role)
    }

    fun getGroupsMembershipByUserId(userId: Long, groupId: Long): GroupsMembership {
        return groupsMembershipInteractor.execute(userId, groupId)
    }
}
