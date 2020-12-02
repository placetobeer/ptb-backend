package com.placeToBeer.groupService.entities


import javax.persistence.*


@Entity
//@Table() //schema = "memberships"
data class Membership(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val membershipID: Int,
        @ManyToOne()
        val group: Group,
        @ManyToOne()
        val member : User,
        var role : Role) {
        constructor(): this(0, Group(), User(), Role.MEMBER) {
        }
}
