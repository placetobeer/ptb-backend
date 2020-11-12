package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.services.GroupService
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.management.loading.ClassLoaderRepository

@RestController
class GroupController (private val groupService: GroupService){

    @GetMapping("/group")
    fun getGroupListByUserId(@RequestParam idUser: Int): List<Group>{
        return groupService.getGroupList(idUser)
    }
}