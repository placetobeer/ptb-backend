package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.exceptions.EmailIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception

internal class EmailFormatValidatorPluginTest {

    private val emailFormatValidatorPlugin = EmailFormatValidatorPlugin()

    private var exception: Exception? = null

    private val validEmail = "iam@valid.de"
    private val invalidEmail = "iam@notvalid"

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun validateWithValidEmail_ThenDoNothing() {
        doValidate(validEmail)
        Assertions.assertThat(exception).isNull()
    }

    @Test
    fun validateWithInvalidEmail_ThenThrowEmailIsInvalidException() {
        doValidate(invalidEmail)
        Assertions.assertThat(exception).isInstanceOf(EmailIsInvalidException::class.java)
    }

    private fun doValidate(email: String){
        try {
            emailFormatValidatorPlugin.validate(email)
        } catch (exception: Exception){
            this.exception = exception
        }
    }
}