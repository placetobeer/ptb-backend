package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.responses.InvitationResponse
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.InvitationRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.interactors.invitation.AnswerInvitationInteractor
import com.placeToBeer.groupService.interactors.invitation.InvitationListInteractor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import com.placeToBeer.groupService.interactors.invitation.InvitationListInteractor as InvitationListInteractor1

@Service
class InvitationService(
    private var invitationListInteractor: InvitationListInteractor,
    private var answerInvitationInteractor: AnswerInvitationInteractor
    ) {

    private var logger: Logger = LoggerFactory.getLogger(InvitationService::class.java)

    fun getInvitationsListByUserId(userId: Long): List<InvitationResponse>{
        return invitationListInteractor.execute(userId)
    }

    fun answerInvitationByInvitationId(invitationId: Long, decision: Boolean){
        return answerInvitationInteractor.execute(invitationId, decision)
    }

}