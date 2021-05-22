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
import com.placeToBeer.groupService.entities.responses.InvitationResponse
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.InvitationNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import org.assertj.core.api.Assertions
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
    private val expectedUser = User()
    private val group1 = Group()
    private val group2 = Group()
    private val validInvitationId = 1L
    private val invalidInvitationId = -1L

    val invitation1 = Invitation(validInvitationId,expectedUser.email,expectedUser,User(), group1, Role.MEMBER)
    val invitation2 = Invitation(2,expectedUser.email,expectedUser,User(), group2, Role.MEMBER)
    val invitationResponse1 = InvitationResponse(invitation1)
    val invitationResponse2 = InvitationResponse(invitation2)
    val expectedNewMembership = Membership(invitation1.group, (invitation1.recipient?: 0) as User, invitation1.role)

    private var exception: Exception? = null

    init {
        whenever(invitationRepository.findAll()).thenReturn(listOf(invitation1,invitation2))
        whenever(membershipRepository.save(any())).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }
        whenever(userRepository.findById(validUserId)).thenReturn(Optional.of(validUser))
        whenever(invitationRepository.findById(invitation1.id)).thenReturn(Optional.of(invitation1))
        //whenever(membershipRepository.findByMember())
    }

    @Test
    fun whenExecuteWithExistingInvitationId_ThenCreateNewMembership(){
        val isNewMembership = doExecute(validInvitationId, true)
        assertThat(isNewMembership).isEqualTo(expectedNewMembership)
        assertThat(exception).isNull()
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
        if(this.membershipRepository.findByMember(expectedUser).isNotEmpty()) {
            newMembership = this.membershipRepository.findByMember(expectedUser)[0]
        }
        return newMembership
    }
}

