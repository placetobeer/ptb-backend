package com.placeToBeer.groupService.entities

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
data class Invitation(
    @NotNull
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JoinColumn(name = "RECIPIENT_ID")
    val recipient: User,

    @NotNull
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "EMITTER_ID")
    val emitter: User,

    @NotNull
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JoinColumn(name = "GROUP_ID")
    val group: Group,

    @NotNull
    @JoinColumn(name = "ROLE")
    val role: Role){


    @Id
    @Column(name = "INVITATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    constructor() : this(
        User(),
        User(),
        Group(),
        Role.MEMBER) {}
    constructor(id: Long, recipient: User, emitter: User, group: Group, role: Role
    ): this(recipient, emitter, group, role){
        this.id = id
    }
}