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
    private val validUserId = 1L
    private val shouldUserMemberships = listOf(
            GroupsMembership(1, User(1, "Bea","bea@mail.com"), Role.OWNER),
            GroupsMembership(4, User(2, "Patrick","patrick@mail.com"), Role.ADMIN),
            GroupsMembership(5, User(3, "Lucie","lucie@mail.com"), Role.MEMBER))

    init {
        whenever(membershipService.getGroupsMembershipListByGroupId(validGroupId)).thenReturn(shouldUserMemberships)
    }

    @Test
    fun whenGetMembershipsByGroupId_thenReturnList() {
        val isUserMemberships = membershipController.getMembershipsByGroupId(validGroupId)
        Assertions.assertThat(isUserMemberships).isEqualTo(shouldUserMemberships)
    }

    @Test
    fun whenGetMembershipByUserIdAndGroupId_thenReturnMembership() {
        val shouldMembership = GroupsMembership(4, User(2, "Patrick","patrick@mail.com"), Role.ADMIN)
        whenever(membershipService.getGroupsMembershipByUserIdAndGroupId(validGroupId, validUserId)).thenReturn(shouldMembership)
        val isMembership = membershipController.getMembershipByUserIdAndGroupId(validGroupId, validUserId)
        Assertions.assertThat(isMembership).isEqualTo(shouldMembership)
    }

    @Test
    fun whenDeleteMembership_thenServiceIsCalled() {
        membershipController.deleteMembershipById(1L)
        verify(membershipService, times(1)).deleteMembershipById(1L)
    }

    @Test
    fun whenSetRole_thenServiceIsCalled() {
        membershipController.setRole(Role.MEMBER, 1L)
        verify(membershipService, times(1)).setRole(1, Role.MEMBER)
    }
}