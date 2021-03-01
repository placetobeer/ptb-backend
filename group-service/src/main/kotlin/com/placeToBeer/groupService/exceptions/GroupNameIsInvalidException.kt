package com.placeToBeer.groupService.exceptions

class GroupNameIsInvalidException(groupName: String) : AbstractInvalidValueException("Group Name", groupName)