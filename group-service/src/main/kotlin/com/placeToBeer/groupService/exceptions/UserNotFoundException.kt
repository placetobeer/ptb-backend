package com.placeToBeer.groupService.exceptions

class UserNotFoundException(userId: Long) : AbstractNotFoundException("Could not find user with userId $userId")