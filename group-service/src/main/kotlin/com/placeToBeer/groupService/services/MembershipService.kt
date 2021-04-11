package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.responses.UsersMembership
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.interactors.membership.GetUsersMembershipListInteractor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MembershipService(private val getUsersMembershipListInteractor: GetUsersMembershipListInteractor) {

    fun getUserMembershipListByGroupId(groupId: Long): List<UsersMembership> {
        return getUsersMembershipListInteractor.execute(groupId)
    }

}