package com.placeToBeer.groupService.interactors.invitation

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.entities.*
import com.placeToBeer.groupService.entities.requests.InvitationItem
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.exceptions.EmailIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.MembershipNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.InvalidInvitationsValidatorPlugin
import com.placeToBeer.groupService.plugins.MembershipExistValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*
import java.util.Optional.empty
import java.util.Optional.of

class CreateInvitationInteractorTest {

    private val groupRepository: GroupRepository = mock()
    private val invitationRepository: InvitationRepository = mock()
    private val membershipRepository: MembershipRepository = mock()
    private val groupExistValidatorPlugin: GroupExistValidatorPlugin = mock()
    private val userExistValidatorPlugin: UserExistValidatorPlugin = mock()
    private val membershipExistValidatorPlugin: MembershipExistValidatorPlugin = mock()
    private val invalidInvitationsValidatorPlugin: InvalidInvitationsValidatorPlugin = mock()

    private val createInvitationInteractor = CreateInvitationInteractor(groupRepository, invitationRepository, membershipRepository,
        groupExistValidatorPlugin, userExistValidatorPlugin, membershipExistValidatorPlugin, invalidInvitationsValidatorPlugin)

    private val invitationList: MutableList<InvitationItem> = mutableListOf(
        InvitationItem("a.b@gmail.com", true)
    )

    private val validGroup = Group(1, "Bratis Kartoffeln")
    private val validEmitter = User(4, "Patrick")
    private val validMembership = Membership(40, validGroup, validEmitter, Role.OWNER)
    private val validInvitationRequest = InvitationRequest(validGroup.id!!, validEmitter, invitationList)
    private val validInvitation = Invitation( "a.b@gmail.com", validEmitter, validGroup, Role.ADMIN)

    private val invalidGroup = Group(-1, "Bad group")
    private val invalidEmitter = User(-1, "Bad emitter")
    private val wrongGroup = Group(5, "Ultimate FrisBees")
    private val invalidMembership = Membership(-1, wrongGroup, validEmitter, Role.OWNER)

    private val expectedInvitationList: List<Invitation> = listOf(validInvitation)

    private var exception: Exception? = null

    @BeforeEach
    fun setUp(){
        exception = null
    }

    init {
        whenever(groupRepository.findById(validGroup.id!!)).thenReturn(Optional.of(validGroup))
        whenever(groupRepository.findById(invalidGroup.id!!)).thenReturn(Optional.of(invalidGroup))
        whenever(groupRepository.findById(wrongGroup.id!!)).thenReturn(Optional.of(wrongGroup))
        whenever(membershipRepository.findByMemberAndGroup(validEmitter, validGroup)).thenReturn(Optional.of(validMembership))
        whenever(membershipRepository.findByMemberAndGroup(validEmitter, wrongGroup)).thenReturn(Optional.of(invalidMembership))
        whenever(invitationRepository.save(any())).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(validGroup), validGroup.id!!)).thenReturn(validGroup)
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(wrongGroup), wrongGroup.id!!)).thenReturn(wrongGroup)
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(invalidGroup), invalidGroup.id!!)).thenThrow(GroupNotFoundException::class.java)
        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(validEmitter), validEmitter.id!!)).thenReturn(validEmitter)
        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(invalidEmitter), invalidEmitter.id!!)).thenThrow(UserNotFoundException::class.java)
        whenever(membershipExistValidatorPlugin.validateAndReturn(Optional.of(validMembership), validMembership.id!!)).thenReturn(validMembership)
        whenever(membershipExistValidatorPlugin.validateAndReturn(Optional.of(invalidMembership), invalidMembership.id!!)).thenThrow(MembershipNotFoundException::class.java)
    }

    @Test
    fun whenExecuteWithValidInvitationRequest_ThenReturnSavedInvitations(){
        val isSavedInvitations = doExecute(validInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isEqualTo(expectedInvitationList)
        Assertions.assertThat(exception).isNull()
        expectedInvitationList.forEach {verify(invitationRepository, times(1)).save(it)}
    }

    @Test
    fun whenExecuteWithNonExistingEmitter_ThenThrowUserNotFoundException(){
        val invalidInvitationRequest = InvitationRequest(validGroup.id!!, invalidEmitter, invitationList)

        val isSavedInvitations = doExecute(invalidInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun whenExecuteWithNonExistingGroup_ThenThrowGroupNotFoundException(){
        val invalidInvitationRequest = InvitationRequest(invalidGroup.id!!, validEmitter, invitationList)

        val isSavedInvitations = doExecute(invalidInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(GroupNotFoundException::class.java)
    }

    @Test
    fun whenExecuteIfUserNotMember_ThenThrowMembershipNotFoundException(){
        val invalidInvitationRequest = InvitationRequest(wrongGroup.id!!, validEmitter, invitationList)

        val isSavedInvitations = doExecute(invalidInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(MembershipNotFoundException::class.java)
    }

    @Test
    fun whenExecuteWithNoValidEmailFormat_ThenThrowEmailIsInvalidException(){
        val invalidEmail = "iam@wrong"
        val invalidInvitationList: MutableList<InvitationItem> = mutableListOf(
            InvitationItem(invalidEmail, true)
        )
        val invalidInvitationRequest = InvitationRequest(validGroup.id!!, validEmitter, invalidInvitationList)
        val invalidInvitation = Invitation( invalidEmail, validEmitter, validGroup, Role.ADMIN)
        whenever(invalidInvitationsValidatorPlugin.validate(invalidInvitation)).thenThrow(EmailIsInvalidException(invalidEmail))

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