package com.placeToBeer.groupService.restPresenters


import com.placeToBeer.groupService.services.InvitationService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import javassist.tools.web.BadHttpRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/invitations")
class InvitationController (private val invitationService: InvitationService){

    @ApiOperation(value = "Receive list of invitations for specific user")
    @GetMapping(value = [""], params = ["userId", "groupId"], produces = ["application/json;charset=UTF-8"])

    fun getInvitationsListByUserId(@RequestParam(required = false, defaultValue = "-1")
                                   @ApiParam(value = "userId", required = false)
                                   userId: Long = -1L,
                                   @RequestParam(required = false, defaultValue = "-1")
                                   @ApiParam(value = "groupId", required = false)
                                   groupId: Long = -1L): List<Any> {
        if(userId != -1L)
            return invitationService.getInvitationsListByUserId(userId)
        if(groupId != -1L)
            return invitationService.getGroupInvitationListByGroupId(groupId)
        throw BadHttpRequest()
    }

    @ApiOperation(value = "Create or do not create a membership based on an invitation and delete the invitation")
    @PutMapping(value = ["/{invitationId}/answer"], produces = ["application/json;charset=UTF-8"])
    fun answerInvitationByInvitationId(@RequestBody decision: Boolean,  @PathVariable invitationId: Long){
        invitationService.answerInvitationByInvitationId(invitationId, decision)
    }

    /*
    //todo delete later
    @ApiOperation("Receive list of groupsInvitations by groupId" )
    @GetMapping(value = [""], params = ["groupId"], produces = ["application/json;charset=UTF-8"])
    fun getGroupsInvitationsListBy(@RequestParam groupId: Long): List<GroupInvitation>{
        return invitationService.getGroupInvitationListByGroupId(groupId)
    }
     */
}