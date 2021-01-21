package com.placeToBeer.groupService.exceptions

import java.lang.RuntimeException

abstract class AbstractNotFoundException(message: String): RuntimeException(message)