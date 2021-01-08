package com.placeToBeer.groupService.services

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class GroupServiceTest {

    private val mockMembershipRepository: MembershipRepository = mock()
    private val mockUserRepository: UserRepository = mock()

    private val groupService = GroupService(mockMembershipRepository, mockUserRepository)

    private val userId = 1L
    private var expectedGroupList: List<Group> = emptyList()

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

}