package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GroupService {

    private var logger: Logger = LoggerFactory.getLogger(GroupService::class.java)

    fun getGroupList(userId: Int): List<Group>{
        //mocks
        /*
        val membership1 = Membership(1,Group(1,"ClubCrew"), User(1, "Tom"), Role.MEMBER)
        val membership2 = Membership(2,Group(1,"ClubCrew"), User(2, "Patrick"), Role.MEMBER)
        val membership3 = Membership(3,Group(2, "Hüttengaudis"),User(1, "Tom"), Role.MEMBER)
        val membership4 = Membership(4, Group(2, "Hüttengaudis"), User(3, "Lucie"), Role.MEMBER)
        obsulete
        */
        val groupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew"), Group(2, "Hüttengaudis"), Group(3, "Corga"))
        logger.warn("Returning mock data for getGroupList by userId. Should be implemented")
        return groupList
    }
}