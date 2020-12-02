package com.placeToBeer.groupService.restPresenters

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.services.GroupService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class GroupControllerTest {

    private val mockGroupService: GroupService = mock()

    private val groupController = GroupController(mockGroupService)

    @Test
    fun whenGetGroupListWithValidId_thenReturnValidAnswer(){
        val userId: Long = 1
        val shouldGroupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew"), Group(2, "Hüttengaudis"), Group(3, "Corga"))





        /* Given */
        whenever(mockGroupService.getGroupList(userId)).thenReturn(shouldGroupList)

        /* When */
        val givenGroupList = groupController.getGroupListByUserId(userId)

        /* Then */
        assertThat(givenGroupList).isEqualTo(shouldGroupList)
    }
}