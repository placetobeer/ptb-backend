package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.services.GroupService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner

//@SpringBootTest
@RunWith(SpringRunner::class)
@WebMvcTest(GroupController::class)
internal class GroupControllerTest(val userId: Int, val groupService: GroupService) {

    @Test
    fun checkCorrectGroupList() {
        var membershipList1: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(2, User(2, "Patrick"), Role.MEMBER))
        var membershipList2: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(3, User(3, "Lucie"), Role.MEMBER))
        var groupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew", membershipList1), Group(2, "Hüttengaudis", membershipList2))
        assertThat(groupList).isEqualTo(groupService.getGroupList(userId))
    }
}