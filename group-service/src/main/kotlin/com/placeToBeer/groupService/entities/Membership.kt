package com.placeToBeer.groupService.entities


import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany


@Entity
class Membership(
        @Id val membershipID: Int,
        member : User,
        var role : Role) {
        constructor() {
        }
}
