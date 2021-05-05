package com.placeToBeer.groupService.interactors.membership

import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.MembershipNotFoundException
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.interactors.group.DeleteGroupInteractor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DeleteMembershipInteractor(private val membershipRepository: MembershipRepository) {

    private var logger: Logger = LoggerFactory.getLogger(DeleteMembershipInteractor::class.java)

    fun execute(membershipId: Long) {
        if(membershipRepository.existsById(membershipId)){
            membershipRepository.deleteById(membershipId)
        } else {
            throwMembershipNotFoundError(membershipId)
        }
    }

    private fun throwMembershipNotFoundError(membershipId: Long) {
        logger.error("No membership with membershipId $membershipId found")
        throw MembershipNotFoundException(membershipId)
    }
}