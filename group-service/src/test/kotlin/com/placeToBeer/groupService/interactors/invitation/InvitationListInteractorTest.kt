package com.placeToBeer.groupService.interactors.invitation

import com.placeToBeer.groupService.gateways.InvitationRepository
import org.assertj.core.api.Assertions.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.InvitationResponse
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class InvitationListInteractorTest {

    private val invitationRepository: InvitationRepository = mock()
    private val userRepository: UserRepository = mock()
    private val groupRepository: GroupRepository = mock()
    private val userExistValidatorPlugin: UserExistValidatorPlugin = mock()
    private val invitationListInteractor= InvitationListInteractor(invitationRepository, userRepository, groupRepository, userExistValidatorPlugin)

    private val validUserId = 1L
    private val invalidUserId = 2L
    private val expectedUser = User()
    private val expectedInvitationResponseList: List<InvitationResponse>


    private var exception: Exception? = null

    init {
        expectedUser.id = validUserId

        val group1 = Group()
        val group2 = Group()


        val invitation1 = Invitation(1,expectedUser.email,expectedUser,User(), group1, Role.MEMBER)
        val invitation2 = Invitation(2,expectedUser.email,expectedUser,User(), group2, Role.MEMBER)
        val invitationResponse1 = InvitationResponse(invitation1)
        val invitationResponse2 = InvitationResponse(invitation2)

        expectedInvitationResponseList = listOf(invitationResponse1, invitationResponse2)

        whenever(userRepository.findById(validUserId)).thenReturn(Optional.of(expectedUser))
        whenever(invitationRepository.findAll()).thenReturn(listOf(invitation1,invitation2))
        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(expectedUser),validUserId)).thenReturn(expectedUser)
        whenever(userExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(UserNotFoundException(0))

    }

    @BeforeEach
    fun setUp(){
        this.exception = null
    }

    @Test
    fun whenExecuteWithExistingUserId_ThenReturnValidInvitationList(){
        val actualInvitationResponseList = doExecute(validUserId)
        assertThat(actualInvitationResponseList!![0].id).isEqualTo(expectedInvitationResponseList[0].id)
        assertThat(actualInvitationResponseList[0].emitter).isEqualTo(expectedInvitationResponseList[0].emitter)
        assertThat(actualInvitationResponseList[0].group).isEqualTo(expectedInvitationResponseList[0].group)
        assertThat(actualInvitationResponseList[0].role).isEqualTo(expectedInvitationResponseList[0].role)
        assertThat(actualInvitationResponseList[1].id).isEqualTo(expectedInvitationResponseList[1].id)
        assertThat(actualInvitationResponseList[1].emitter).isEqualTo(expectedInvitationResponseList[1].emitter)
        assertThat(actualInvitationResponseList[1].group).isEqualTo(expectedInvitationResponseList[1].group)
        assertThat(actualInvitationResponseList[1].role).isEqualTo(expectedInvitationResponseList[1].role)
        assertThat(exception).isNull()
    }

    @Test
    fun whenExecuteNonExistingUserId_ThenThrowUserNotFoundException(){
        val actualInvitationResponseList = doExecute(invalidUserId)
        assertThat(actualInvitationResponseList).isNull()
        assertThat(exception).isExactlyInstanceOf(UserNotFoundException::class.java)
    }

    private fun doExecute(userId: Long): List<InvitationResponse>? {
        var invitationResponseList: List<InvitationResponse>? = null
        try {
            invitationResponseList = invitationListInteractor.execute(userId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return invitationResponseList
    }
}
