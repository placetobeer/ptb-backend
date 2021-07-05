package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.*
import com.placeToBeer.groupService.services.mail.EmailServiceImpl
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateInvitationInteractor(
    private var groupRepository: GroupRepository,
    private var userRepository: UserRepository,
    private var invitationRepository: InvitationRepository,
    private var membershipRepository: MembershipRepository,
    private var userRegisteredValidatorPlugin: UserRegisteredValidatorPlugin,
    private var groupExistValidatorPlugin: GroupExistValidatorPlugin,
    private var userExistValidatorPlugin: UserExistValidatorPlugin,
    private var membershipExistValidatorPlugin: MembershipExistValidatorPlugin,
    private var invalidInvitationsValidatorPlugin: InvalidInvitationsValidatorPlugin,
    private var emailServiceImpl: EmailServiceImpl
) {
    fun execute(invitationRequest: InvitationRequest): List<Invitation> {
        return createInvitations(invitationRequest)
    }

    private fun createInvitations(invitationRequest: InvitationRequest): List<Invitation> {
        val savedInvitations: MutableList<Invitation> = mutableListOf()
        validateEmitterAndGroup(invitationRequest.emitter, getGroupByGroupId(invitationRequest.groupId))
        for (invitation in mapToInvitationEntity(invitationRequest)) {
            invalidInvitationsValidatorPlugin.validate(invitation)
            savedInvitations.add(invitationRepository.save(invitation))
        }
        return savedInvitations
    }

    private fun validateEmitterAndGroup(emitter: User, group: Group){
        userExistValidatorPlugin.validateAndReturn(Optional.of(emitter), emitter.id!!)
        groupExistValidatorPlugin.validateAndReturn(Optional.of(group), group.id!!)
        val possibleMembership = membershipRepository.findByMemberAndGroup(emitter, group)
        membershipExistValidatorPlugin.validateAndReturn(possibleMembership, possibleMembership.get().id!!)
    }

    private fun mapToInvitationEntity(invitationRequest: InvitationRequest): List<Invitation> {
        val invitationEntityList: MutableList<Invitation> = mutableListOf()
        for ( invitation in invitationRequest.invitationList ) {
            var role = Role.MEMBER
            if (invitation.grantAdmin){
                role = Role.ADMIN
            }
            val receiver = getReceiverByEmail(invitation.email)
            invitationEntityList.add(Invitation(
                invitation.email,
                receiver,
                invitationRequest.emitter,
                getGroupByGroupId(invitationRequest.groupId),
                role))
            if(receiver == null){
                sendInvitationMail(
                    invitation.email,
                    invitationRequest.emitter.name,
                    getGroupByGroupId(invitationRequest.groupId).name,
                    role)
            }
        }
        return invitationEntityList
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
