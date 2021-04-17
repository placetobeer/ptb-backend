package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.entities.responses.InvitationResponse
import com.placeToBeer.groupService.services.InvitationService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/invitations")
class InvitationController (private val invitationService: InvitationService){



    @ApiOperation(value = "Receive list of invitations for specific user")
    @GetMapping(value = [""], params = ["userId"], produces = ["application/json;charset=UTF-8"])
    fun getInvitationsListByUserId(@RequestParam userId: Long): List<InvitationResponse> {
        return invitationService.getInvitationsListByUserId(userId)
    }
}