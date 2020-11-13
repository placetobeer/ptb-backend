package com.placeToBeer.groupService.entities

import javax.persistence.Entity
import javax.persistence.Id

@Entity
public class Group(
        @Id val id: Int,
        var name: String,
        var memberships: MutableList<Membership> = mutableListOf()) {
}