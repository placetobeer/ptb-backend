package com.placeToBeer.groupService.restPresenters

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.services.MembershipService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class MembershipControllerTest {

    private val membershipService: MembershipService = mock()
    private val membershipController = MembershipController(membershipService)

    private val validGroupId = 1L
    private val shouldUserMemberships = listOf(
            GroupsMembership(1, User(1, "Bea","bea@mail.com"), Role.OWNER),
            GroupsMembership(4, User(2, "Patrick","patrick@mail.com"), Role.ADMIN),
            GroupsMembership(5, User(3, "Lucie","lucie@mail.com"), Role.MEMBER))

    init {
        whenever(membershipService.getGroupsMembershipListByGroupId(validGroupId)).thenReturn(shouldUserMemberships)
    }

    @Test
    fun whenGetUsersByGroupId_ThenReturnList() {
        val isUserMemberships = membershipController.getMembershipsByGroupId(validGroupId)
        Assertions.assertThat(isUserMemberships).isEqualTo(shouldUserMemberships)
    }

    @Test
    fun whenDeleteMembership_ThenServiceIsCalled() {
        membershipController.deleteMembershipById(1L)
        verify(membershipService, times(1)).deleteMembershipById(1L)
    }
}