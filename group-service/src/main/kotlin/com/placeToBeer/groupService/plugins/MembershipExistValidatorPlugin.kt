package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.exceptions.MembershipNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class MembershipExistValidatorPlugin {

    private var logger: Logger = LoggerFactory.getLogger(MembershipExistValidatorPlugin::class.java)

    fun validateAndReturn(membership: Optional<Membership>, membershipId: Long): Membership {
        if(membership.isEmpty){
            throwMembershipNotFoundError(membershipId)
        }
        return membership.get()
    }

    private fun throwMembershipNotFoundError(membershipId: Long) {
        logger.error("No membership with membershipId $membershipId found")
        throw MembershipNotFoundException(membershipId)
    }
}