package com.placeToBeer.groupService.exceptions

class EmailIsInvalidException(email: String) : AbstractInvalidValueException("Email", email)