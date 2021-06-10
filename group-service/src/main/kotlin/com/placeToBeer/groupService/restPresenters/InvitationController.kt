package com.placeToBeer.groupService.restPresenters


import com.placeToBeer.groupService.entities.responses.GroupInvitation
import com.placeToBeer.groupService.services.InvitationService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import javassist.tools.web.BadHttpRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/invitations")
class InvitationController (private val invitationService: InvitationService){

    @ApiOperation(value = "Receive list of invitations for specific user")
    @GetMapping(value = ["/byUser"], params = ["userId"], produces = ["application/json;charset=UTF-8"])
    fun getInvitationsListByUserId(@RequestParam userId: Long): List<Any> {
        return invitationService.getInvitationsListByUserId(userId)
    }

    @ApiOperation(value = "Create or do not create a membership based on an invitation and delete the invitation")
    @PutMapping(value = ["/{invitationId}/answer"], produces = ["application/json;charset=UTF-8"])
    fun answerInvitationByInvitationId(@RequestBody decision: Boolean,  @PathVariable invitationId: Long){
        invitationService.answerInvitationByInvitationId(invitationId, decision)
    }


    @ApiOperation("Receive list of groupsInvitations by groupId" )
    @GetMapping(value = ["/byGroup"], params = ["groupId"], produces = ["application/json;charset=UTF-8"])
    fun getGroupsInvitationsListByGroupId(@RequestParam groupId: Long): List<GroupInvitation>{
        return invitationService.getGroupInvitationListByGroupId(groupId)
    }
}