package com.placeToBeer.groupService.exceptions

import java.lang.RuntimeException

class GroupNotFoundException(groupId: Long): RuntimeException("Could not find group with groupId $groupId")