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

    private val mockMembershipRepository: MembershipRepository = mock()
    private val mockUserRepository: UserRepository = mock()
    private val mockGroupRepository: GroupRepository = mock()

    private val groupService = GroupService(mockMembershipRepository, mockUserRepository, mockGroupRepository)

    private val userId = 1L
    private var expectedGroupList: List<Group> = emptyList()
    private var expectedNewGroup: Group? = null

    private var exception: Exception? = null

    init {
        val user = User()
        user.id = userId

        val group1 = Group()
        val group2 = Group()
        expectedGroupList = listOf(group1, group2)

        val membership1 = Membership()
        membership1.group = group1
        membership1.member = user
        val membership2 = Membership()
        membership2.group = group2
        membership2.member = user

        whenever(mockUserRepository.findById(userId)).thenReturn(Optional.of(user))
        whenever(mockMembershipRepository.findByMember(user)).thenReturn(listOf(membership1, membership2))

        expectedNewGroup = Group(userId, "Testgruppe")
    }

    @BeforeEach
    fun init(){
        exception = null
    }


    @Test
    fun whenGetGroupListWithExistingUserId_ThenReturnValidGroupList() {
        val isGroupList = doGroupListByUserId(userId)
        Assertions.assertThat(isGroupList).isEqualTo(expectedGroupList)
    }


    @Test
    fun whenGetGroupListWithNonExistingUserId_ThenThrowUserNotFoundException(){
        val isGroupList = doGroupListByUserId(2)
        Assertions.assertThat(isGroupList).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(UserNotFoundException::class.java)
    }

    private fun doGroupListByUserId(userId: Long): List<Group>? {
        var groupList: List<Group>? = null
        try {
            groupList = groupService.getGroupListByUserId(userId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return groupList
    }

    @Test
    fun whenCreateGroupWithExistingUserId_ThenReturnNewGroup(){
        val isNewGroup = doCreateGroup(userId)
        Assertions.assertThat(isNewGroup).isEqualTo(expectedNewGroup)
    }

    @Test
    fun whenCreateGroupWithNonExistingUserId_ThenThrowUserNotFoundException(){
        val isNewGroup = doCreateGroup(2)
        Assertions.assertThat(isNewGroup).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(UserNotFoundException::class.java)
    }

    private fun doCreateGroup(userId: Long): Group? {
        var newGroup: Group? = null
        try {
            newGroup = groupService.createGroup(userId, "Testgruppe")
        } catch (exception: Exception){
            this.exception = exception
        }
        return newGroup
    }

    @Test
    fun whenCreateOwnershipWithExistingGroup_ThenSaveOwnership(){
        val user = User(userId, "")
        val group = Group("Test Group")
        val expectedOwnership = Membership(group, user, Role.OWNER)

        doCreateOwnership(userId, group)

        verify(mockMembershipRepository).save(argThat {
            this == expectedOwnership
        })
    }

    @Test
    fun whenCreateOwnershipWithNonExistingGroup_ThenThrowGroupNotFoundException(){
        val group = Group("Not existing")
        whenever(groupService.createOwnership(userId, group)).thenThrow(GroupNotFoundException::class.java)

        doCreateOwnership(userId, group)

        Assertions.assertThat(this.exception).isExactlyInstanceOf(GroupNotFoundException::class.java)
    }

    private fun doCreateOwnership(userId: Long, group: Group) {
        try {
            groupService.createOwnership(userId, group)
        } catch (exception: Exception){
            this.exception = exception
        }
    }

}