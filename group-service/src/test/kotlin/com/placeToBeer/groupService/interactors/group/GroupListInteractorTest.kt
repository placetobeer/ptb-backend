package com.placeToBeer.groupService.interactors.group

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class GroupListInteractorTest {

    private val userRepository: UserRepository = mock()
    private val membershipRepository: MembershipRepository = mock()
    private val userExistValidatorPlugin: UserExistValidatorPlugin = mock()
    private val groupListInteractor = GroupListInteractor(membershipRepository, userRepository, userExistValidatorPlugin)

    private val validUserId = 1L
    private val invalidUserId = 2L
    private val expectedUser = User()
    private val expectedGroupList: List<Group>

    private var exception: Exception? = null

    init {
        expectedUser.id = validUserId

        val group1 = Group()
        val group2 = Group()
        expectedGroupList = listOf(group1, group2)

        val membership1 = Membership()
        membership1.group = group1
        membership1.member = expectedUser
        val membership2 = Membership()
        membership2.group = group2
        membership2.member = expectedUser

        whenever(userRepository.findById(validUserId)).thenReturn(Optional.of(expectedUser))
        whenever(membershipRepository.findByMember(expectedUser)).thenReturn(listOf(membership1, membership2))

        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(expectedUser), validUserId)).thenReturn(expectedUser)
        whenever(userExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(UserNotFoundException(0))
    }

    @BeforeEach
    fun setUp() {
        this.exception = null
    }

    @Test
    fun whenExecuteWithExistingUserId_ThenReturnValidGroupList() {
        val actualGroupList = doExecute(validUserId)
        Assertions.assertThat(actualGroupList).isEqualTo(expectedGroupList)
        Assertions.assertThat(exception).isNull()
    }

    @Test
    fun whenExecuteNonExistingUserId_ThenThrowUserNotFoundException(){
        val isGroupList = doExecute(invalidUserId)
        Assertions.assertThat(isGroupList).isNull()
        Assertions.assertThat(exception).isExactlyInstanceOf(UserNotFoundException::class.java)
    }

    private fun doExecute(userId: Long): List<Group>? {
        var groupList: List<Group>? = null
        try {
            groupList = groupListInteractor.execute(userId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return groupList
    }
}