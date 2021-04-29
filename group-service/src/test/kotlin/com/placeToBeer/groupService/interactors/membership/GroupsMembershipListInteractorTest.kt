package com.placeToBeer.groupService.interactors.membership

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class GroupsMembershipListInteractorTest{

    private val groupRepository: GroupRepository = mock()
    private val membershipRepository: MembershipRepository = mock()
    private val groupExistValidatorPlugin: GroupExistValidatorPlugin = mock()

    private val groupsMembershipListInteractor = GroupsMembershipListInteractor(groupRepository, membershipRepository, groupExistValidatorPlugin)

    private val validGroupId = 1L
    private val invalidGroupId = 2L
    private val validGroup = Group(validGroupId, "groupName")

    private val expectedGroupMembershipList: List<GroupsMembership>

    private var exception: Exception? = null

    init {
        val user1 = User(1L, "user1")
        val user2 = User(2L, "user2")

        val membership1 = Membership(1L, validGroup, user1, Role.OWNER)
        val groupsMembership1 = GroupsMembership(membership1)

        val membership2 = Membership(2L, validGroup, user2, Role.MEMBER)
        val groupsMembership2 = GroupsMembership(membership2)

        expectedGroupMembershipList = listOf(groupsMembership1, groupsMembership2)

        whenever(groupRepository.findById(validGroupId)).thenReturn(Optional.of(validGroup))
        whenever(membershipRepository.findByGroup(validGroup)).thenReturn(listOf(membership1, membership2))
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(validGroup), validGroupId)).thenReturn(validGroup)
        whenever(groupExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(GroupNotFoundException(0))
    }

    @BeforeEach
    fun setUp() {
        this.exception = null
    }

    @Test
    fun whenExecuteWithExistingGroupId_ThenReturnValidGroupMembershipList() {
        val actualGroupMembershipList = doExecute(validGroupId)
        Assertions.assertThat(actualGroupMembershipList).isEqualTo(expectedGroupMembershipList)
        Assertions.assertThat(exception).isNull()
    }

    @Test
    fun whenExecuteNonExistingGroupId_ThenThrowGroupNotFoundException(){
        val actualGroupMembershipList = doExecute(invalidGroupId)
        Assertions.assertThat(actualGroupMembershipList).isNull()
        Assertions.assertThat(exception).isExactlyInstanceOf(GroupNotFoundException::class.java)
    }

    private fun doExecute(groupId: Long): List<GroupsMembership>? {
        var groupMembershipList: List<GroupsMembership>? = null
        try {
            groupMembershipList = groupsMembershipListInteractor.execute(groupId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return groupMembershipList
    }
}