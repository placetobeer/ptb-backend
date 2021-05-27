package com.placeToBeer.groupService.interactors.invitation

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.InvitationExistsValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import com.placeToBeer.groupService.entities.*
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.InvitationNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException

import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*
import org.assertj.core.api.Assertions.*



internal class AnswerInvitationInteractorTest {
    private val invitationRepository: InvitationRepository = mock()
    private val membershipRepository: MembershipRepository = mock()
    private val userRepository: UserRepository = mock()
    private val groupRepository: GroupRepository = mock()
    private val invitationExistsValidatorPlugin: InvitationExistsValidatorPlugin = mock()
    private val userExistValidatorPlugin: UserExistValidatorPlugin = mock()
    private val groupExistValidatorPlugin: GroupExistValidatorPlugin = mock()
    private val answerInvitationInteractor = AnswerInvitationInteractor(invitationRepository, membershipRepository, userRepository, groupRepository, invitationExistsValidatorPlugin, userExistValidatorPlugin, groupExistValidatorPlugin)

    private val validUserId = 1L
    private val validUser = User(validUserId, "name")
    private val invalidUserId = 2L
    private val validGroupId = 1L
    private val group1 = Group(validGroupId,"testGroup")
    private val validInvitationId = 1L
    private val invalidInvitationId = -1L


    private val invitation1 = Invitation(validInvitationId,validUser.email,validUser,User(), group1, Role.MEMBER)
    private val expectedNewMembership = Membership(invitation1.group, (invitation1.recipient?: 0) as User, invitation1.role)

    private var exception: Exception? = null

    init {

        whenever(invitationRepository.findAll()).thenReturn(listOf(invitation1))
        whenever(membershipRepository.save(any<Membership>())).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }
        whenever(userRepository.findById(validUserId)).thenReturn(Optional.of(validUser))
        whenever(userRepository.findById(invalidUserId)).thenReturn(Optional.empty())
        whenever(groupRepository.findById(validGroupId)).thenReturn(Optional.of(group1))

        whenever(invitationRepository.findById(validInvitationId)).thenReturn(Optional.of(invitation1))
        whenever(invitationRepository.delete(invitation1)).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }

        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(validUser), validUserId)).thenReturn(validUser)
        whenever(userExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(UserNotFoundException(0))
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(group1), validGroupId)).thenReturn(group1)
        whenever(groupExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(GroupNotFoundException(0))
        whenever(invitationExistsValidatorPlugin.validateAndReturn(Optional.of(invitation1), validInvitationId)).thenReturn(invitation1)
        whenever(invitationExistsValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(InvitationNotFoundException(0))
    }

    @Test
    fun whenExecuteWithExistingInvitationId_ThenCreateNewMembership(){
        val isNewMembership = doExecute(validInvitationId, true)
        assertThat(exception).isNull()
        assertThat(isNewMembership?.group).isEqualTo(expectedNewMembership.group)
        assertThat(isNewMembership?.member).isEqualTo(expectedNewMembership.member)
        assertThat(isNewMembership?.role).isEqualTo(expectedNewMembership.role)
        verify(membershipRepository, times(1)).save(expectedNewMembership)
    }

    @Test
    fun whenExecuteWithExistingInvitationIdDeny_ThenDoNotCreateNewMembership(){
        val isNewMembership = doExecute(validInvitationId, false)
        assertThat(isNewMembership).isNull()
        assertThat(exception).isNull()
    }

    @Test
    fun whenExecuteWithInvalidInvitationId_ThenThrowInvitationNotFoundException(){
        val isNewMembership = doExecute(invalidInvitationId, true)
        assertThat(isNewMembership).isNull()
        assertThat(this.exception).isExactlyInstanceOf(InvitationNotFoundException::class.java)
    }

    private fun doExecute(invitationId:Long, decision: Boolean): Membership?{
        var newMembership: Membership? = null
        try {
            answerInvitationInteractor.execute(invitationId , decision)
        } catch (exception: Exception){
            this.exception = exception
        }
        if(decision){
            try {
                val invitation = invitationExistsValidatorPlugin.validateAndReturn(invitationRepository.findById(invitationId), invitationId)
                newMembership = invitation.recipient?.let { Membership(invitation.group, it, invitation.role) }
            } catch (exception: Exception){

            }
        }
        return newMembership
    }
}

