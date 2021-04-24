package com.placeToBeer.groupService.interactors.group

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.gateways.GroupRepository
import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.Test

import java.lang.Exception

internal class DeleteGroupInteractorTest {

    private val groupRepository: GroupRepository = mock()
    private val deleteGroupInteractor = DeleteGroupInteractor(groupRepository)

    private val validGroupId = 1L
    private val invalidGroupId = 2L

    init {
        whenever(groupRepository.existsById(1L)).thenReturn(true)
        whenever(groupRepository.existsById(2L)).thenReturn(false)
    }

    @Test
    fun whenExecuteWithExistingGroup_ThenNoError() {
        var exception: Exception? = null
        try {
            deleteGroupInteractor.execute(validGroupId)
        } catch (e: Exception) {
            exception = e
        }
        Assertions.assertNull(exception)
        verify(groupRepository, times(1)).deleteById(validGroupId)
    }

    @Test
    fun whenExecuteWithExistingGroup_ThenError() {
        var exception: Exception? = null
        try {
            deleteGroupInteractor.execute(invalidGroupId)
        } catch (e: Exception) {
            exception = e
        }
        Assertions.assertNotNull(exception)
        verify(groupRepository, never()).deleteById(any())
    }
}