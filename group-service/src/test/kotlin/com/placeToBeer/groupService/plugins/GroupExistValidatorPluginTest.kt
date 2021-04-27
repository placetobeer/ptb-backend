package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.lang.Exception
import java.util.*

internal class GroupExistValidatorPluginTest {

    private val groupExistValidatorPlugin = GroupExistValidatorPlugin()

    private var exception: Exception? = null

    private val validGroupId = 1L
    private val validGroup = Group(validGroupId, "groupName")

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun validateAndReturnWithExistingGroup_ThenReturnGroup() {
        val group = doValidateAndReturn(Optional.of(validGroup), validGroupId)
        Assertions.assertThat(exception).isNull()
        Assertions.assertThat(group).isEqualTo(validGroup)
    }

    @Test
    fun validateAndReturnWithEmptyGroup_ThenThrowGroupNotFoundError() {
        val group = doValidateAndReturn(Optional.empty(), validGroupId)
        Assertions.assertThat(exception).isInstanceOf(GroupNotFoundException::class.java)
        Assertions.assertThat(group).isNull()
    }

    private fun doValidateAndReturn(group: Optional<Group>, groupId: Long): Group? {
        var realGroup: Group? = null
        try {
            realGroup = groupExistValidatorPlugin.validateAndReturn(group, groupId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return realGroup
    }
}