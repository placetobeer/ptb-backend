package com.placeToBeer.groupService.exceptions

class GroupNotFoundException(groupId: Long): AbstractNotFoundException("Could not find group with groupId $groupId")