package com.placeToBeer.groupService.restPresenters

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.UserMembership
import com.placeToBeer.groupService.services.MembershipService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class MembershipControllerTest {

    private val membershipService: MembershipService = mock()
    private val membershipController = MembershipController(membershipService)

    private val groupId = 1L
    private val shouldUserMemberships = listOf(
            UserMembership(User(1, "Bea"), Role.OWNER),
            UserMembership(User(2, "Patrick"), Role.ADMIN),
            UserMembership(User(3, "Lucie"), Role.MEMBER))

    init {
        whenever(membershipService.getUserMembershipListByGroupId(groupId)).thenReturn(shouldUserMemberships)
    }

    @Test
    fun whenGetUsersByGroupId_ThenReturnList() {
        val isUserMemberships = membershipController.getMembershipsByGroupId(groupId)
        Assertions.assertThat(isUserMemberships).isEqualTo(shouldUserMemberships)
    }
}