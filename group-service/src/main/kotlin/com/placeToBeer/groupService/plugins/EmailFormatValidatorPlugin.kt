package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.exceptions.EmailIsInvalidException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class EmailFormatValidatorPlugin {

    private var logger: Logger = LoggerFactory.getLogger(EmailFormatValidatorPlugin::class.java)

    fun validate(email: String) {
        if (email.isNotEmpty() && !Pattern.compile(
                "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}\$"
            ).matcher(email).matches()) {
            throwEmailIsInvalidError(email)
        }
    }

    private fun throwEmailIsInvalidError(email: String) {
        logger.error("The email $email is not a valid email address")
        throw EmailIsInvalidException(email)
    }
}