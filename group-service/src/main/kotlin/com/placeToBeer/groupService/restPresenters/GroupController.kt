package com.placeToBeer.groupService.restPresenters

import com.fasterxml.jackson.databind.node.TextNode
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.services.GroupService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.boot.json.JacksonJsonParser
import org.springframework.web.bind.annotation.*

@RestController
@Api(description = "Operations directly related to groups")
@RequestMapping("/groups")
class GroupController (private val groupService: GroupService){

    @ApiOperation(value = "View list of groups that refer to the user by membership")
    @GetMapping(value = [""], params = ["userId"], produces = ["application/json;charset=UTF-8"])
    fun getGroupListByUserId(@RequestParam userId: Long): List<Group> {
        return groupService.getGroupListByUserId(userId)
    }

    @ApiOperation(value = "Create group with owner and group name and return new group")
    @PostMapping(value = [""], params = ["userId", "groupName"], produces = ["application/json;charset=UTF-8"])
    fun createGroup(@RequestParam userId: Long, @RequestParam groupName: String): Group {
        return groupService.createGroup(userId, groupName)
    }

    @ApiOperation(value = "Set group name")
    @PutMapping(value = ["/{groupId}/name"], produces = ["application/json;charset=UTF-8"])
    fun setGroupName(@RequestBody groupName: TextNode, @PathVariable groupId: Long){
        groupService.setGroupNameByGroupId(groupId, groupName.asText())
    }

}
