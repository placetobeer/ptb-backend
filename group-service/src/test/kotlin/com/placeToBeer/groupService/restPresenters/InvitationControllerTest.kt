package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.services.InvitationService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.*
import com.placeToBeer.groupService.entities.requests.InvitationItem
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.entities.responses.InvitationResponse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class InvitationControllerTest(
    //private var membershipRepository: MembershipRepository
    ) {
    private val mockInvitationService: InvitationService = mock()
    private val invitationController = InvitationController(mockInvitationService)
    private val userId: Long = 2

    private val userA = User(1,"Bea", "bea@mail.com")
    private val userB = User(2, "Patrick","patrick@mail.com")
    private val userC = User(3, "Lucie","lucie@mail.com")

    private val groupA = Group(1,"ClubCrew")
    private val groupB = Group(2,"Hüttengaudis")

    private val invitationList: MutableList<Invitation> = mutableListOf(
        Invitation(1,userB.email, userB, userA, groupA, Role.MEMBER),
        Invitation(2,userB.email, userB, userA, groupB, Role.ADMIN),
        Invitation(3,userC.email, userC, userA, groupA, Role.MEMBER)
    )

    @Test
    fun whenValidRecipientId_thenReturnInvitationResponseList(){
        val shouldInvitationResponseList: MutableList<InvitationResponse> = mutableListOf(
            InvitationResponse(1, userA, groupA, Role.MEMBER),
            InvitationResponse(2,userA, groupB, Role.ADMIN)
        )

        /*Given*/
        whenever(mockInvitationService.getInvitationsListByUserId(userId)).thenReturn(shouldInvitationResponseList)

        /*When*/
        val givenInvitationResponseList = invitationController.getInvitationsListByUserId(userId = userId)

        /*Then*/
        Assertions.assertThat(givenInvitationResponseList).isEqualTo(shouldInvitationResponseList)
    }

    @Test
    fun whenCreateInvitations_thenReturnInvitations(){
        val invitationList: MutableList<InvitationItem> = mutableListOf(
            InvitationItem("a.b@gmail.com", true)
        )
        val invitationRequest = InvitationRequest(1, User(4, "Patrick"), invitationList)
        val shouldInvitationList: List<Invitation> = listOf(
            Invitation("a.b@gmail.com", User(4, "Patrick"), Group(1, "Bratis Kartoffeln"), Role.ADMIN)
        )
        whenever(mockInvitationService.createInvitations(invitationRequest)).thenReturn(shouldInvitationList)

        val givenInvitationList = invitationController.createInvitations(invitationRequest)

        Assertions.assertThat(givenInvitationList).isEqualTo(shouldInvitationList)
    }

    /*
    2 other UseCases:
    whenAnswerInvitation_thenCreateMembership()
    whenAnswerInvitation_thenDeleteInvitation()
    cannot be testet (no return)

    @Test
    fun whenAnswerInvitation_thenCreateMembership(){
        val shouldMembership = Membership(invitationList[0].group, invitationList[0].recipient, invitationList[0].role)
        /*Given*/
        whenever(mockInvitationService.answerInvitationByInvitationId(1, true))

        /*When*/
        mockInvitationService.answerInvitationByInvitationId(1, true)
        val givenMembershipList: MutableList<Membership> = membershipRepository.findAll() as MutableList<Membership>

        /*Then*/
        Assertions.assertThat(givenMembershipList[givenMembershipList.count()-1]).isEqualTo(shouldMembership)

    }

    @Test
    fun whenAnswerInvitation_thenDeleteInvitation(){
        val shouldInvitationList: MutableList<Invitation> = mutableListOf(
            Invitation(2,userB, userA, groupB, Role.ADMIN),
            Invitation(3,userC, userA, groupA, Role.MEMBER)
        )

        /*Given*/
        whenever(mockInvitationService.answerInvitationByInvitationId(1, true))

        /*When*/
        mockInvitationService.answerInvitationByInvitationId(1, true)
        val givenInvitationList: MutableList<Invitation> = invitationList

        /*Then*/
        Assertions.assertThat(givenInvitationList).isEqualTo(shouldInvitationList)
    }
     */
}