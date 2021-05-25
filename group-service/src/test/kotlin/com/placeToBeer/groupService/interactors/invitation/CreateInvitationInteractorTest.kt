package com.placeToBeer.groupService.interactors.invitation

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.requests.InvitationItem
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.exceptions.EmailIsInvalidException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotOwnerException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.InvalidInvitationsValidatorPlugin
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

class CreateInvitationInteractorTest {

    private val groupRepository: GroupRepository = mock()
    private val invitationRepository: InvitationRepository = mock()
    private val groupExistValidatorPlugin: GroupExistValidatorPlugin = mock()
    private val invalidInvitationsValidatorPlugin: InvalidInvitationsValidatorPlugin = mock()

    private val createInvitationInteractor = CreateInvitationInteractor(groupRepository, invitationRepository, groupExistValidatorPlugin, invalidInvitationsValidatorPlugin)

    private val invitationList: MutableList<InvitationItem> = mutableListOf(
        InvitationItem("a.b@gmail.com", true)
    )

    private val validGroup = Group(1, "Bratis Kartoffeln")
    private val validInvitationRequest = InvitationRequest(1, User(4, "Patrick"), invitationList)

    private val validInvitation = Invitation( "a.b@gmail.com", User(4, "Patrick"), validGroup, Role.ADMIN)
    private val expectedInvitationList: List<Invitation> = listOf(validInvitation)

    private var exception: Exception? = null

    @BeforeEach
    fun setUp(){
        exception = null
    }

    init {
        whenever(groupRepository.findById(validGroup.id!!)).thenReturn(Optional.of(validGroup))
        whenever(invitationRepository.save(any())).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(validGroup), validGroup.id!!)).thenReturn(validGroup)
    }

    @Test
    fun whenExecuteWithValidInvitationRequest_ThenReturnSavedInvitations(){
        val isSavedInvitations = doExecute(validInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isEqualTo(expectedInvitationList)
        Assertions.assertThat(exception).isNull()
        expectedInvitationList.forEach {verify(invitationRepository, times(1)).save(it)}
    }

    @Test
    fun whenExecuteWithNonExistingUserId_ThenThrowUserNotFoundException(){
        val invalidUser = User(-1, "Patrick")
        val invalidInvitationRequest = InvitationRequest(1, invalidUser, invitationList)
        val invalidInvitation = Invitation( "a.b@gmail.com", invalidUser, validGroup, Role.ADMIN)
        whenever(invalidInvitationsValidatorPlugin.validate(invalidInvitation)).thenThrow(UserNotFoundException(-1))

        val isSavedInvitations = doExecute(invalidInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun whenExecuteIfUserNotOwner_ThenThrowUserNotOwnerException(){
        val invalidOwner = User(3, "Katja")
        val invalidInvitationRequest = InvitationRequest(1, invalidOwner, invitationList)
        val invalidInvitation = Invitation( "a.b@gmail.com", invalidOwner, validGroup, Role.ADMIN)
        whenever(invalidInvitationsValidatorPlugin.validate(invalidInvitation)).thenThrow(UserNotOwnerException(3))

        val isSavedInvitations = doExecute(invalidInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(UserNotOwnerException::class.java)
    }

    @Test
    fun whenExecuteWithNoValidEmailFormat_ThenThrowEmailIsInvalidException(){
        val invalidInvitationList: MutableList<InvitationItem> = mutableListOf(
            InvitationItem("iam@wrong", true)
        )
        val invalidInvitationRequest = InvitationRequest(1, User(4, "Patrick"), invalidInvitationList)
        val invalidInvitation = Invitation( "iam@wrong", User(4, "Patrick"), validGroup, Role.ADMIN)
        whenever(invalidInvitationsValidatorPlugin.validate(invalidInvitation)).thenThrow(EmailIsInvalidException("iam@wrong"))

        val isSavedInvitations = doExecute(invalidInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(EmailIsInvalidException::class.java)
    }

    private fun doExecute(invitationRequest: InvitationRequest): List<Invitation>? {
        var savedInvitations: List<Invitation>? = null
        try {
            savedInvitations = createInvitationInteractor.execute(invitationRequest)
        } catch (exception: Exception){
            this.exception = exception
        }
        return savedInvitations
    }
}