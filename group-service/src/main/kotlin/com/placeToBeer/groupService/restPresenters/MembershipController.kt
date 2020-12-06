package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.entities.responses.UserMembership
import com.placeToBeer.groupService.services.MembershipService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(description = "Operations directly related to memberships")
@RequestMapping("/memberships")
class MembershipController(private val membershipService: MembershipService) {

    @ApiOperation(value = "View list of memberships without groupdata that refer to a group")
    @GetMapping(value = [""], params = ["groupId"], produces = ["application/json;charset=UTF-8"])
    fun getMembershipsByGroupId(@RequestParam groupId: Long): List<UserMembership> {
        return membershipService.getMembershipList(groupId)
    }
}