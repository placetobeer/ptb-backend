package com.placeToBeer.groupService.services

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.UsersMembership
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class MembershipServiceTest {

    /*

    private val groupRepository: GroupRepository = mock()
    private val membershipRepository: MembershipRepository = mock()

    private val membershipService = MembershipService(groupRepository, membershipRepository)

    private val groupId = 1L
    private val shouldUsersMemberships: MutableList<UsersMembership> = mutableListOf()

    private var exception: Exception? = null

    init {
        val group = Group(groupId, "Bratis Kartoffeln")

        val membership1 = Membership(1, group, User(1, "Bea"), Role.OWNER)
        shouldUsersMemberships.add(UsersMembership(membership1))
        val membership2 = Membership(2, group, User(2, "Patrick"), Role.ADMIN)
        shouldUsersMemberships.add(UsersMembership(membership2))
        val membership3 = Membership(3, group, User(3, "Lucie"), Role.MEMBER)
        shouldUsersMemberships.add(UsersMembership(membership3))
        val membership4 = Membership(4, group, User(4, "Tom"), Role.MEMBER)
        shouldUsersMemberships.add(UsersMembership(membership4))
        val memberships = listOf(membership1, membership2, membership3, membership4)

        whenever(groupRepository.findById(groupId)).thenReturn(Optional.of(group))
        whenever(membershipRepository.findByGroup(group)).thenReturn(memberships)
    }

    @BeforeEach
    fun init(){
        exception = null
    }

    @Test
    fun whenGetMembershipListWithExistingGroup_ThenReturnValidList() {
        val isUserMemberships = doUserMembershipListByUserId(groupId)
        Assertions.assertThat(isUserMemberships).isNotEmpty.isEqualTo(shouldUsersMemberships)
    }

    @Test
    fun whenGetMembershipListWithNonExistingGroup_ThenThrowException() {
        val isUserMemberships = doUserMembershipListByUserId(2)
        Assertions.assertThat(isUserMemberships).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(GroupNotFoundException::class.java)
    }

    private fun doUserMembershipListByUserId(groupId: Long): List<UsersMembership>? {
        var usersMembershipList: List<UsersMembership>? = null
        try {
            usersMembershipList = membershipService.getUserMembershipListByGroupId(groupId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return usersMembershipList
    }

     */
}