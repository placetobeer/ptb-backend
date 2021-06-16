package com.placeToBeer.groupService.entities.requests

import com.placeToBeer.groupService.entities.ActivityType

data class ProposalRequest(
        var name: String,
        var groupId: Long,
        var activityType: ActivityType)