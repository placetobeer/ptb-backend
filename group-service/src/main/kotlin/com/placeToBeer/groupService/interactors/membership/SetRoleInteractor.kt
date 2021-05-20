package com.placeToBeer.groupService.interactors.membership

import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.plugins.MembershipExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class SetRoleInteractor(private val membershipRepository: MembershipRepository, private val membershipExistValidatorPlugin: MembershipExistValidatorPlugin ) {

    fun execute(membershipId: Long, role: Role) {
        val membership = getMembershipById(membershipId)
        membership.role = role
        membershipRepository.save(membership)
    }

    private fun getMembershipById(membershipId: Long): Membership {
        val possibleMembership = membershipRepository.findById(membershipId)
        return membershipExistValidatorPlugin.validateAndReturn(possibleMembership, membershipId)
    }

}