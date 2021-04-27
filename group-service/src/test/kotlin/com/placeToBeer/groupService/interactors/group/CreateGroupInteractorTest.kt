package com.placeToBeer.groupService.interactors.group

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import com.placeToBeer.groupService.plugins.GroupNameValidatorPlugin
import com.placeToBeer.groupService.plugins.UserExistValidatorPlugin
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

import java.lang.Exception
import java.util.*

internal class CreateGroupInteractorTest {


    private val userRepository: UserRepository = mock()
    private val groupRepository: GroupRepository = mock()
    private val membershipRepository: MembershipRepository = mock()
    private val userExistValidatorPlugin: UserExistValidatorPlugin = mock()
    private val groupNameValidatorPlugin: GroupNameValidatorPlugin = mock()

    private val createGroupListInteractor = CreateGroupInteractor(groupRepository, userRepository, membershipRepository, groupNameValidatorPlugin, userExistValidatorPlugin)


    private val validUserId = 1L
    private val validUser = User(validUserId, "Karl")
    private val invalidUserId = 2L

    private val expectedNewGroup = Group(validUserId, "Testgruppe")
    private val expectedMembership = Membership(expectedNewGroup, validUser, Role.OWNER)

    private val validGroupName = "Testgruppe"
    private val invalidGroupName = ""

    private var exception: Exception? = null

    init {
        whenever(userRepository.findById(validUserId)).thenReturn(Optional.of(validUser))
        whenever(userRepository.findById(invalidUserId)).thenReturn(Optional.empty())

        whenever(groupRepository.save(any())).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }

        whenever(groupNameValidatorPlugin.validate(invalidGroupName)).thenThrow(GroupNameIsInvalidException(invalidGroupName))

        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(validUser), validUserId)).thenReturn(validUser)
        whenever(userExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(UserNotFoundException(0))
    }

    @BeforeEach
    fun setUp(){
        exception = null
    }


    @Test
    fun whenExecuteWithExistingUserId_ThenReturnNewGroup(){
        val isNewGroup = doExecute(validUserId, validGroupName)
        Assertions.assertThat(isNewGroup).isEqualTo(expectedNewGroup)
        Assertions.assertThat(exception).isNull()
        verify(groupRepository, times(1)).save(expectedNewGroup)
        verify(membershipRepository, times(1)).save(expectedMembership)
    }

    @Test
    fun whenExecuteWithNonExistingUserId_ThenThrowUserNotFoundException(){
        val isNewGroup = doExecute(invalidUserId, validGroupName)
        Assertions.assertThat(isNewGroup).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun whenExecuteWithInvalidGroupName_ThenThrowInvalidGroupNameException(){
        val isNewGroup = doExecute(validUserId, invalidGroupName)
        Assertions.assertThat(isNewGroup).isNull()
        Assertions.assertThat(this.exception).isExactlyInstanceOf(GroupNameIsInvalidException::class.java)
    }

    private fun doExecute(userId: Long, groupName: String): Group? {
        var newGroup: Group? = null
        try {
            newGroup = createGroupListInteractor.execute(userId, groupName)
        } catch (exception: Exception){
            this.exception = exception
        }
        return newGroup
    }

}