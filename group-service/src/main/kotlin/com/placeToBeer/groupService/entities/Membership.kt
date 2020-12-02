package com.placeToBeer.groupService.entities


import org.jetbrains.annotations.NotNull
import javax.persistence.*


@Entity
@Table(name = "membership") //schema = "memberships"
data class Membership(
        @Id
        @NotNull
        @GeneratedValue(strategy = GenerationType.AUTO)
        val membershipID: Long,
        @NotNull
        @ManyToOne()
        val group: Group,
        @NotNull
        @ManyToOne()
        val member : User,
        @NotNull
        var role : Role) {
        constructor(): this(0, Group(), User(), Role.MEMBER) {
        }
}
