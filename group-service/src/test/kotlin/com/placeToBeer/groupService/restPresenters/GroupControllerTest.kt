package com.placeToBeer.groupService.restPresenters

import com.fasterxml.jackson.databind.node.TextNode
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.services.GroupService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


internal class GroupControllerTest {

    private val mockGroupService: GroupService = mock()

    private val groupController = GroupController(mockGroupService)
    private val userId = 1L
    private val groupId = 1L
    private val groupName = "Group name"

    @Test
    fun whenGetGroupListWithValidId_thenReturnValidAnswer(){
        val shouldGroupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew"), Group(2, "Hüttengaudis"),
                Group(3, "Corga"))

        /* Given */
        whenever(mockGroupService.getGroupListByUserId(userId)).thenReturn(shouldGroupList)

        /* When */
        val givenGroupList = groupController.getGroupListByUserId(userId)

        /* Then */
        Assertions.assertThat(givenGroupList).isEqualTo(shouldGroupList)
    }

    @Test
    fun whenCreateGroup_thenReturnCreatedGroup(){
        val shouldGroup = Group(groupName)
        whenever(mockGroupService.createGroup(userId, groupName)).thenReturn(shouldGroup)
        val givenCreatedGroup = groupController.createGroup(userId, groupName)
        Assertions.assertThat(givenCreatedGroup).isEqualTo(shouldGroup)
    }

    @Test
    fun whenSetName_thenServiceIsCalled(){
        val groupName = TextNode("groupName")
        groupController.setGroupName(groupName, groupId)
        verify(mockGroupService, times(1)).setGroupNameByGroupId(groupId, groupName.asText())
    }

    @Test
    fun whenDeleteGroup_thenServiceIsCalled(){
        groupController.deleteGroup(groupId)
        verify(mockGroupService, times(1)).deleteGroup(groupId)
    }
}