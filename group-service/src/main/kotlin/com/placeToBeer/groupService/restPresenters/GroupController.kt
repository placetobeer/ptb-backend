package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.services.GroupService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController

class GroupController (private val groupService: GroupService){

    @GetMapping("/group")
    fun getGroupListByUserId(@RequestParam UserId: Int): List<Group>{
        return groupService.getGroupList(UserId)
    }
}
