package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.services.GroupService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(description = "Operations directly related to groups")
@RequestMapping("/groups")
class GroupController (private val groupService: GroupService){

    @ApiOperation(value = "View list of groups that refer to the user by membership")
    @RequestMapping(value = ["/byUserId/{userId}"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getGroupListByUserId(@PathVariable userId: Int): List<Group>{
        return groupService.getGroupList(userId)
    }
}
