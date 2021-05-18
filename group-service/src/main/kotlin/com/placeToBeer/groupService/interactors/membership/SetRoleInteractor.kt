package com.placeToBeer.groupService.interactors.membership

import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.gateways.MembershipRepository
import org.springframework.stereotype.Component

@Component
class SetRoleInteractor(private val membershipRepository: MembershipRepository) {

    fun execute(membershipId: Long, role: Role) {

    }

}