package com.placeToBeer.groupService.restPresenters

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.services.GroupService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test



internal class GroupControllerTest() {

    @Test
    fun checkGetGroupListByUserId(){
        val userId = 1
        var membershipList1: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(2, User(2, "Patrick"), Role.MEMBER))
        var membershipList2: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(3, User(3, "Lucie"), Role.MEMBER))
        var groupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew", membershipList1), Group(2, "Hüttengaudis", membershipList2))

        /* Given */
        val mock = mock<GroupService> {
            on { getGroupList(userId) } doReturn groupList
        }

        val groupController = GroupController(mock)

        /* When */
        val givenGroupList = groupController.getGroupListByUserId(userId)

        /* Then */
        assertThat(givenGroupList).isEqualTo(groupList)
    }
}