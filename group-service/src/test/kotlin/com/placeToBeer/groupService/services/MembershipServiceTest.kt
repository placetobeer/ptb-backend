package com.placeToBeer.groupService.services

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.interactors.membership.GroupsMembershipListInteractor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class MembershipServiceTest {

    private val groupsMembershipListInteractor: GroupsMembershipListInteractor = mock()

    private val membershipService = MembershipService(groupsMembershipListInteractor)

    private val validGroupId = 1L

    @Test
    fun whenGetGroupsMembershipListByGroupId_ThenInteractorIsExecuted() {
        membershipService.getGroupsMembershipListByGroupId(validGroupId)
        verify(groupsMembershipListInteractor, times(1)).execute(validGroupId)
    }


}