package com.placeToBeer.groupService.services

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.interactors.group.CreateGroupInteractor
import com.placeToBeer.groupService.interactors.group.DeleteGroupInteractor
import com.placeToBeer.groupService.interactors.group.GroupListInteractor
import com.placeToBeer.groupService.interactors.group.SetGroupNameInteractor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.AdditionalAnswers
import java.lang.Exception
import java.util.*
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import java.lang.reflect.Member
import kotlin.test.assertNull


internal class GroupServiceTest {

    private val groupListInteractor: GroupListInteractor = mock()
    private val createGroupInteractor: CreateGroupInteractor = mock()
    private val setGroupNameInteractor: SetGroupNameInteractor = mock()
    private val deleteGroupInteractor: DeleteGroupInteractor = mock()

    private val groupService = GroupService(groupListInteractor, createGroupInteractor, setGroupNameInteractor, deleteGroupInteractor)

    private val validUserId = 1L
    private val validGroupId = 1L
    private val validGroupName = "groupName"

    @Test
    fun whenGetGroupListByUserId_ThenCallInteractor() {
        groupService.getGroupListByUserId(validUserId)
        verify(groupListInteractor, times(1)).execute(validUserId)
    }

    @Test
    fun whenCreateGroup_ThenCallInteractor() {
        groupService.createGroup(validUserId, validGroupName)
        verify(createGroupInteractor, times(1)).execute(validUserId, validGroupName)
    }

    @Test
    fun whenSetGroupNameByGroupId_ThenCallInteractor() {
        groupService.setGroupNameByGroupId(validGroupId, validGroupName)
        verify(setGroupNameInteractor, times(1)).execute(validGroupId, validGroupName)
    }

    @Test
    fun whenDeleteGroup_ThenCallInteractor() {
        groupService.deleteGroup(validGroupId)
        verify(deleteGroupInteractor, times(1)).execute(validGroupId)
    }
}