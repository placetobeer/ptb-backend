package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.services.mail.EmailServiceImpl
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import com.placeToBeer.groupService.plugins.UserRegisteredValidatorPlugin
import org.springframework.stereotype.Component

@Component
class CreateInvitationInteractor(
    private var emailServiceImpl: EmailServiceImpl,
    private var userRepository: UserRepository,
    private var groupRepository: GroupRepository,
    private var invitationRepository: InvitationRepository,
    private var userExistValidatorPlugin: UserExistValidatorPlugin,
    private var groupExistValidatorPlugin: GroupExistValidatorPlugin,
    private var userRegisteredValidatorPlugin: UserRegisteredValidatorPlugin
) {
    fun execute() {
        //TODO
    }

    private fun createNewInvitationByIds(email: String, receiverId: Long, emitterId: Long, groupId: Long, role: Role) {
        val receiver = getReceiverByEmail(email)
        val emitter = getUserByUserId(emitterId)
        val group = getGroupByGroupId(groupId)
        createNewInvitation(email, receiver, emitter, group, role)
    }

    private fun createNewInvitation(email: String, receiver: User?, emitter: User, group: Group, role: Role) {
        val newInvitation = Invitation(
            email,
            receiver,
            emitter,
            group,
            role
        )
        invitationRepository.save(newInvitation)
        if (receiver == null) {sendInvitationMail(email, emitter.name, group.name, role)
        }
    }

    private fun getUserByUserId(userId: Long): User {
            val user = userRepository.findById(userId)
            return userExistValidatorPlugin.validateAndReturn(user, userId)
    }

    private fun getGroupByGroupId(groupId: Long): Group {
            val group = groupRepository.findById(groupId)
            return groupExistValidatorPlugin.validateAndReturn(group, groupId)
    }

    private fun getReceiverByEmail(email: String): User? {
        val user = userRepository.getUserByEmail(email)
        return try{
            userRegisteredValidatorPlugin.validateAndReturn(user, email)
        } catch(e: UserNotFoundException){
            null
        }
    }

    private fun sendInvitationMail(userEmail: String, emitterName: String, groupName: String, role: Role) {
            emailServiceImpl.sendSimpleMessage(
                userEmail,
                "You have got an invitation to \"$groupName\" in PlaceToBeer!",
                "Hi!\n\n you got invited by $emitterName to join the PlaceToBeer - Group: \"$groupName\" as a(n) $role!\n" +
                        "To join this Group, you just need to create a PlaceToBeer-Account using your email-Address!\n" +
                        "We hope to welcome you to our wonderful PlaceToBeer - Community :)\n\n" +
                        "Greetings,\n your PlaceToBeer Team!\n" +
                        "https://placetobeer475840703.wordpress.com/about/\n\n" +
                        "PS: this is an auto-generated mail by the place-to-beer team"
            )
    }
}