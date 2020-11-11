package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam

@Service
class GroupService {
    fun getGroupList(): List<Group>{
        //mocks
        var membershipList1: List<Membership> = listOf(Membership(User(1, "Tom"), Role.MEMBER), Membership(User(2, "Patrick"), Role.MEMBER))
        var membershipList2: List<Membership> = listOf(Membership(User(1, "Tom"), Role.MEMBER), Membership(User(3, "Lucie"), Role.MEMBER))
        var groupList: List<Group> = listOf(Group(1, "ClubCrew", membershipList1), Group(2, "Hüttengaudis", membershipList2))
        return groupList
    }

    /*fun getGroupList(@RequestParam idUser: Int){
        return
    }*/
}