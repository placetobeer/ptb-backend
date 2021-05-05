package com.placeToBeer.groupService.interactors.group

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

import java.lang.Exception

internal class DeleteGroupInteractorTest {

    private val groupRepository: GroupRepository = mock()
    private val deleteGroupInteractor = DeleteGroupInteractor(groupRepository)

    private val validGroupId = 1L
    private val invalidGroupId = 2L

    var exception: Exception? = null

    init {
        whenever(groupRepository.existsById(validGroupId)).thenReturn(true)
        whenever(groupRepository.existsById(invalidGroupId)).thenReturn(false)
    }

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun whenExecuteWithExistingGroup_ThenNoError() {
        doExecute(validGroupId)
        assertThat(exception).isNull()
        verify(groupRepository, times(1)).deleteById(validGroupId)
    }

    @Test
    fun whenExecuteWithNonExistingGroup_ThenError() {
        doExecute(invalidGroupId)
        assertThat(exception).isInstanceOf(GroupNotFoundException::class.java)
        verify(groupRepository, never()).deleteById(any())
    }

    private fun doExecute(groupId: Long){
        try {
            deleteGroupInteractor.execute(groupId)
        } catch (e: Exception) {
            exception = e
        }
    }
}