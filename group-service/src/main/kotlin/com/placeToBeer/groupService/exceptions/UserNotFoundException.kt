package com.placeToBeer.groupService.exceptions

class UserNotFoundException(userId: Long) : RuntimeException("Could not find user with userId $userId")