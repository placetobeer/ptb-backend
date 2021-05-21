package com.placeToBeer.groupService.exceptions

class UserNotOwnerException(userId: Long) : AbstractNotFoundException("The user with the userId $userId is not owner of the group")