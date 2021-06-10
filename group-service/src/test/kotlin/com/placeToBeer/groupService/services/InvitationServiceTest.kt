package com.placeToBeer.groupService.services

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.requests.InvitationItem
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.interactors.group.GroupListInteractor
import com.placeToBeer.groupService.interactors.invitation.AnswerInvitationInteractor
import com.placeToBeer.groupService.interactors.invitation.CreateInvitationInteractor
import com.placeToBeer.groupService.interactors.invitation.GroupInvitationListInteractor
import com.placeToBeer.groupService.interactors.invitation.InvitationListInteractor
import org.junit.jupiter.api.Test

internal class InvitationServiceTest {

    private val createInvitationInteractor: CreateInvitationInteractor = mock()
    private val invitationListInteractor: InvitationListInteractor = mock()
    private val answerInvitationInteractor: AnswerInvitationInteractor = mock()
    private val groupInvitationListInteractor: GroupInvitationListInteractor = mock()

    private val invitationService = InvitationService(createInvitationInteractor, invitationListInteractor, answerInvitationInteractor, groupInvitationListInteractor)

    private val invitationList: MutableList<InvitationItem> = mutableListOf(
        InvitationItem("a.b@gmail.com", true),
        InvitationItem("test@mail.de", false)
    )
    private val invitationRequest = InvitationRequest(1, User(4, "Patrick"), invitationList)

    @Test
    fun whenCreateInvitations_ThenCallInteractor() {
        invitationService.createInvitations(invitationRequest)
        verify(createInvitationInteractor, times(1)).execute(invitationRequest)
    }
}