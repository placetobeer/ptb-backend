package com.placeToBeer.groupService.services

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.UserMembership
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class MembershipServiceTest {

    private val groupRepository: GroupRepository = mock()
    private val membershipRepository: MembershipRepository = mock()

    private val membershipService = MembershipService(groupRepository, membershipRepository)

    private val groupId = 1L
    private val shouldUserMemberships: MutableList<UserMembership> = mutableListOf()

    init {
        val group = Group(groupId, "Bratis Kartoffeln")

        val membership1 = Membership(1, group, User(1, "Bea"), Role.OWNER)
        shouldUserMemberships.add(UserMembership(membership1))
        val membership2 = Membership(2, group, User(2, "Patrick"), Role.ADMIN)
        shouldUserMemberships.add(UserMembership(membership2))
        val membership3 = Membership(3, group, User(3, "Lucie"), Role.MEMBER)
        shouldUserMemberships.add(UserMembership(membership3))
        val membership4 = Membership(4, group, User(4, "Tom"), Role.MEMBER)
        shouldUserMemberships.add(UserMembership(membership4))
        val memberships = listOf(membership1, membership2, membership3, membership4)

        whenever(groupRepository.findById(groupId)).thenReturn(Optional.of(group))
        whenever(membershipRepository.findByGroup(group)).thenReturn(memberships)
    }

    @Test
    fun whenGetMembershipListWithExistingGroup_ThenReturnValidList() {
        val isUserMemberships = membershipService.getMembershipList(groupId)
        Assertions.assertThat(isUserMemberships).isNotEmpty.isEqualTo(shouldUserMemberships)
    }

    @Test
    fun whenGetMembershipListWithNonExistingGroup_ThenReturnEmptyList() {
        val isUserMemberships = membershipService.getMembershipList(2)
        Assertions.assertThat(isUserMemberships).isEmpty()
    }
}