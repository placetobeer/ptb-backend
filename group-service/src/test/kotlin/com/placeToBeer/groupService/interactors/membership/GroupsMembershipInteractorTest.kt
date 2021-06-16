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
import com.placeToBeer.groupService.exceptions.MembershipNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.MembershipExistValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class GroupsMembershipInteractorTest {

    private val userRepository: UserRepository = mock()
    private val membershipRepository: MembershipRepository = mock()
    private val groupRepository: GroupRepository = mock()
    private val userExistValidatorPlugin: UserExistValidatorPlugin = mock()
    private val groupExistValidatorPlugin: GroupExistValidatorPlugin = mock()
    private val membershipExistValidatorPlugin: MembershipExistValidatorPlugin = mock()

    private val groupsMembershipInteractor = GroupsMembershipInteractor(userRepository, membershipRepository, groupRepository, userExistValidatorPlugin, groupExistValidatorPlugin, membershipExistValidatorPlugin)

    private val validUserId = 1L
    private val validUser = User(validUserId, "userName")
    private val invalidUserId = -1L
    private val validUserId2 = 2L
    private val validUser2 = User(validUserId2, "userName")
    private val validGroupId = 1L
    private val validGroup = Group(validGroupId, "groupName")
    private val invalidGroupId = -1L
    private val validGroupId2 = 2L
    private val validGroup2 = Group(validGroupId2, "groupName")

    private val expectedGroupsMembership: GroupsMembership

    private var exception: Exception? = null

    init {
        val membership = Membership(1L, validGroup, validUser, Role.OWNER)
        expectedGroupsMembership = GroupsMembership(membership)

        whenever(groupRepository.findById(validGroupId)).thenReturn(Optional.of(validGroup))
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(validGroup), validGroupId)).thenReturn(validGroup)
        whenever(groupExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(GroupNotFoundException(0))
        whenever(membershipRepository.findByMemberAndGroup(validUser, validGroup)).thenReturn(Optional.of(membership))
        whenever(membershipExistValidatorPlugin.validateAndReturn(Optional.of(membership), membership.id!!)).thenReturn(membership)
        whenever(userRepository.findById(validUserId)).thenReturn(Optional.of(validUser))
        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(validUser), validUserId)).thenReturn(validUser)
        whenever(userExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(UserNotFoundException(0))
    }

    @BeforeEach
    fun setUp() {
        this.exception = null
    }

    @Test
    fun whenExecuteWithExistingGroupIdAndUserId_ThenReturnValidGroupMembership() {
        val shouldGroupsMembership = doExecute(validUserId, validGroupId)
        Assertions.assertThat(shouldGroupsMembership).isEqualTo(expectedGroupsMembership)
        Assertions.assertThat(exception).isNull()
    }

    @Test
    fun whenExecuteWithNonExistingGroupId_ThenThrowGroupNotFoundException(){
        val shouldGroupsMembership = doExecute(validUserId, invalidGroupId)
        Assertions.assertThat(shouldGroupsMembership).isNull()
        Assertions.assertThat(exception).isExactlyInstanceOf(GroupNotFoundException::class.java)
    }

    @Test
    fun whenExecuteWithNonExistingUserId_ThenThrowUserNotFoundException(){
        val shouldGroupsMembership = doExecute(invalidUserId, validGroupId)
        Assertions.assertThat(shouldGroupsMembership).isNull()
        Assertions.assertThat(exception).isExactlyInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun whenExecuteWithNonExistingMembership_ThenThrowMembershipNotFoundException(){
        val invalidMembership = Membership(-1L, validGroup2, validUser2, Role.OWNER)
        whenever(groupRepository.findById(validGroupId2)).thenReturn(Optional.of(validGroup2))
        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(validGroup2), validGroupId2)).thenReturn(validGroup2)
        whenever(userRepository.findById(validUserId2)).thenReturn(Optional.of(validUser2))
        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(validUser2), validUserId2)).thenReturn(validUser2)
        whenever(membershipRepository.findByMemberAndGroup(validUser2, validGroup2)).thenReturn(Optional.of(invalidMembership))
        whenever(membershipExistValidatorPlugin.validateAndReturn(Optional.of(invalidMembership), invalidMembership.id!!)).thenThrow(MembershipNotFoundException(0))

        val shouldGroupsMembership = doExecute(validUserId2, validGroupId2)

        Assertions.assertThat(shouldGroupsMembership).isNull()
        Assertions.assertThat(exception).isExactlyInstanceOf(MembershipNotFoundException::class.java)
    }

    private fun doExecute(userId: Long, groupId: Long): GroupsMembership? {
        var membership: GroupsMembership? = null
        try {
            membership = groupsMembershipInteractor.execute(userId, groupId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return membership
    }
}