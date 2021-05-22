package com.placeToBeer.groupService.interactors.invitation

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.requests.InvitationItem
import com.placeToBeer.groupService.entities.requests.InvitationRequest
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

    private val validInvitation = Invitation( "a.b@gmail.com", User(4, "Patrick"),  validGroup, Role.ADMIN)
    private val expectedInvitationList: List<Invitation> = listOf(validInvitation)

    private var exception: Exception? = null

    @BeforeEach
    fun setUp(){
        exception = null
    }

    init {
        whenever(groupRepository.findById(validGroup.id)).thenReturn(Optional.of(validGroup))
        whenever(invitationRepository.save(any())).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(validGroup), validGroup.id!!)).thenReturn(validGroup)
    }

    @Test
    fun whenExecuteWithValidInvitationRequest_ThenReturnSavedInvitations(){
        whenever(invalidInvitationsValidatorPlugin.validate(validInvitation)).then { this.exception == null }
        val isSavedInvitations = doExecute(validInvitationRequest)

        Assertions.assertThat(isSavedInvitations).isEqualTo(expectedInvitationList)
        Assertions.assertThat(exception).isNull()
        expectedInvitationList.forEach {verify(invitationRepository, times(1)).save(it)}
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