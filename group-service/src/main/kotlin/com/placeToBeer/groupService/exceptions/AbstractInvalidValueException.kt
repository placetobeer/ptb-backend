package com.placeToBeer.groupService.exceptions

abstract class AbstractInvalidValueException(invalidField: String, invalidValue: String): RuntimeException("$invalidField with value $invalidValue is invalid")