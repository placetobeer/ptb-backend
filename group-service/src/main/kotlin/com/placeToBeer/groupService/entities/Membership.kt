package com.placeToBeer.groupService.entities


import javax.persistence.*


@Entity
class Membership(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val membershipID: Int,
        @ManyToOne()
        val member : User,
        var role : Role) {
        constructor(): this(0,User(), Role.MEMBER) {
        }
}
