package com.placeToBeer.groupService.entities


import javax.persistence.*


@Entity
class Membership(
        @Id val membershipID: Int,
        @ManyToOne()
        val member : User,
        var role : Role) {
        constructor() {
        }
}
