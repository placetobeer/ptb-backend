package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GroupService (private var membershipRepository: MembershipRepository, private var userRepository: UserRepository){

    private var logger: Logger = LoggerFactory.getLogger(GroupService::class.java)

    fun getGroupList(userId: Long): List<Group>{
        //mock
        val mockGroupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew"), Group(2, "Hüttengaudis"), Group(3, "Corga"))
        logger.warn("Returning mock data for getGroupList by userId. Should be implemented")

        /*real code
        val groupList = mutableListOf<Group>()
        val user = userRepository.findById(userId)
        if(user.isEmpty){
            logger.error("No user with userId $userId found")
            return emptyList()
        }
        val memberships: List<Membership> = membershipRepository.findByMember(user.get())
        for(membership in memberships){
            groupList.add(membership.group)
        }
         */

        return mockGroupList
    }
}