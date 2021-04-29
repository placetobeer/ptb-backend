package com.placeToBeer.groupService.interactors.group

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.GroupNameValidatorPlugin
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.lang.Exception
import java.util.*

internal class SetGroupNameInteractorTest {

    private var groupRepository: GroupRepository = mock()
    private var groupNameValidatorPlugin: GroupNameValidatorPlugin = mock()
    private var groupExistValidatorPlugin: GroupExistValidatorPlugin = mock()

    private val setGroupNameInteractor = SetGroupNameInteractor(groupRepository, groupNameValidatorPlugin, groupExistValidatorPlugin)

    private val validGroupId = 1L
    private val invalidGroupId = 2L
    private val validGroup = Group(validGroupId, "validGroup")
    private val validGroupName = "testGroupName"
    private val invalidGroupName = ""

    private var exception: Exception? = null

    init {
        whenever(groupRepository.findById(validGroupId)).thenReturn(Optional.of(validGroup))
        whenever(groupRepository.findById(invalidGroupId)).thenReturn(Optional.empty())

        whenever(groupNameValidatorPlugin.validate(invalidGroupName)).thenThrow(GroupNameIsInvalidException(invalidGroupName))

        whenever(groupExistValidatorPlugin.validateAndReturn(Optional.of(validGroup), validGroupId)).thenReturn(validGroup)
        whenever(groupExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(GroupNotFoundException(0))
    }

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun executeWithValidGroupIdAndGroupName_thenReturnNoError() {
        doExecute(validGroupId, validGroupName)
        val newGroup = Group(validGroupId, validGroupName)
        verify(groupRepository, times(1)).save(newGroup)
        Assertions.assertThat(exception).isNull()
    }

    @Test
    fun executeWithInvalidGroupId_thenReturnError() {
        doExecute(invalidGroupId, validGroupName)
        verify(groupRepository, never()).save(any())
        Assertions.assertThat(exception).isInstanceOf(GroupNotFoundException::class.java)
    }

    @Test
    fun executeWithInvalidGroupName_thenReturnError() {
        doExecute(validGroupId, invalidGroupName)
        verify(groupRepository, never()).save(any())
        Assertions.assertThat(exception).isInstanceOf(GroupNameIsInvalidException::class.java)
    }

    private fun doExecute(groupId: Long, groupName: String) {
        try {
            setGroupNameInteractor.execute(groupId, groupName )
        } catch (exception: Exception){
            this.exception = exception
        }
    }
}