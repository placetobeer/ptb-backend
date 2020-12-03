package com.placeToBeer.groupService.services

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class GroupServiceTest {

    private val mockMembershipRepository: MembershipRepository = mock()
    private val mockUserRepository: UserRepository = mock()

    private val groupService = GroupService(mockMembershipRepository, mockUserRepository)


    private val userId = 1L
    private var shouldGroupList: List<Group> = emptyList()

    init {
        val user = User()
        user.id = userId

        val group1 = Group()
        val group2 = Group()
        shouldGroupList = listOf(group1, group2)

        val membership1 = Membership()
        membership1.group = group1
        membership1.member = user
        val membership2 = Membership()
        membership2.group = group2
        membership2.member = user

        whenever(mockUserRepository.findById(userId)).thenReturn(Optional.of(user))
        whenever(mockMembershipRepository.findByMember(user)).thenReturn(listOf(membership1, membership2))
    }


    @Test
    fun whenGetGroupListWithExistingUser_ThenReturnValidGroupList() {
        val isGroupList = groupService.getGroupList(userId)
        Assertions.assertThat(isGroupList).isNotEmpty.isEqualTo(shouldGroupList)
    }


    @Test
    fun whenGetGroupListWithNonExistingUser_ThenReturnEmptyGroupList(){
        val isGroupList = groupService.getGroupList(2)
        Assertions.assertThat(isGroupList).isEmpty()
    }

}