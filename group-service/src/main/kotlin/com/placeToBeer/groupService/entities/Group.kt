package com.placeToBeer.groupService.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Group(
        @Id val id: Int,
        var name: String,
        @OneToMany()
        var memberships: MutableList<Membership>) {
    constructor() {
    }

}