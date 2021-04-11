package com.placeToBeer.groupService.entities.responses

import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import io.swagger.annotations.ApiModelProperty
import org.jetbrains.annotations.NotNull

data class GroupsMembership(
        @NotNull
        @ApiModelProperty(
                value = "User",
                example = "1",
                required = true)
        var user: User,

        @NotNull
        @ApiModelProperty(
                value = "Users role in group",
                example = "1",
                required = true)
        var role: Role) {

    constructor(membership: Membership) : this(membership.member, membership.role)

}
