package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.services.GroupService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@Api(description = "Operations directly related to groups")
@RequestMapping("/groups")
class GroupController (private val groupService: GroupService){

    @ApiOperation(value = "View list of groups that refer to the user by membership")
    @GetMapping(value = [""], params = ["userId"], produces = ["application/json;charset=UTF-8"])
    fun getGroupListByUserId(@RequestParam userId: Int): List<Group> {
        return groupService.getGroupList(userId)
    }
}
