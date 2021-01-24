package com.placeToBeer.groupService.entities


import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
data class Membership(
        @NotNull
        @ManyToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
        @JoinColumn(name = "GROUP_ID", insertable = false, updatable = false)
        var group: Group,
        @NotNull
        @ManyToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
        @JoinColumn(name = "MEMBER_ID", insertable = false, updatable = false)
        var member : User,
        @NotNull
        var role : Role) {

        @Id
        @Column(name="MEMBERSHIP_ID")
        @NotNull
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null

        constructor(): this(Group(), User(), Role.MEMBER) {}
        constructor(id: Long, group: Group, member: User, role: Role): this(group, member, role){
                this.id = id
        }
}
