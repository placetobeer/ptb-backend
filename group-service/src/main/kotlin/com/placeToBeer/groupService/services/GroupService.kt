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
        val membershipList1: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(2, User(2, "Patrick"), Role.MEMBER))
        val membershipList2: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(3, User(3, "Lucie"), Role.MEMBER))
        val groupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew", membershipList1), Group(2, "Hüttengaudis", membershipList2))
        logger.warn("Returning mock data for getGroupList by userId. Should be implemented")
        return groupList
    }
}