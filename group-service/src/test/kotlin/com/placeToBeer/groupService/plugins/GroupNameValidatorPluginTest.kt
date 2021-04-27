package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class GroupNameValidatorPluginTest{

    private val groupNameValidatorPlugin = GroupNameValidatorPlugin()

    private var exception: Exception? = null

    private val validGroupName = "groupName"
    private val emptyGroupName = ""

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun validateWithValidGroupName_ThenDoNothing() {
        doValidate(validGroupName)
        Assertions.assertThat(exception).isNull()
    }

    @Test
    fun validateWithEmptyGroupName_ThenThrowGroupNameIsInvalidException() {
        doValidate(emptyGroupName)
        Assertions.assertThat(exception).isInstanceOf(GroupNameIsInvalidException::class.java)
    }

    private fun doValidate(groupName: String){
        try {
            groupNameValidatorPlugin.validate(groupName)
        } catch (exception: Exception){
            this.exception = exception
        }
    }
}