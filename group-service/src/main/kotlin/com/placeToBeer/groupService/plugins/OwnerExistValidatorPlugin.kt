package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotOwnerException
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class OwnerExistValidatorPlugin(
    private var membershipRepository: MembershipRepository,
    private var userRepository: UserRepository,
    private var userExistValidatorPlugin: UserExistValidatorPlugin,
    private var membershipExistValidatorPlugin: MembershipExistValidatorPlugin
) {

    private var logger: Logger = LoggerFactory.getLogger(OwnerExistValidatorPlugin::class.java)

    fun validate(invitation: Invitation) {
        val owner = userRepository.findById(invitation.emitter.id!!)
        userExistValidatorPlugin.validateAndReturn(owner, invitation.emitter.id!!)
        val ownerMembership = membershipRepository.findByMemberAndGroup(owner.get(), invitation.group);
        membershipExistValidatorPlugin.validateAndReturn(ownerMembership, ownerMembership.get().id!!)
        if(ownerMembership.get().role != Role.OWNER){
            throwUserNotOwnerError(invitation.emitter.id!!)
        }
    }

    private fun throwUserNotOwnerError(userId: Long) {
        logger.error("The user with the userId $userId is not owner of the group")
        throw UserNotOwnerException(userId)
    }

}