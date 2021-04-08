package com.placeToBeer.groupService.entities

import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
data class Invitation(
    @NotNull
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "RECIPIENT_ID")
    val recipient: User,

    @NotNull
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "EMITTER_ID")
    val emitter: User,

    @NotNull
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "GROUP_ID")
    val group: Group){

    @Id
    @Column(name = "INVITATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    /*@NotNull
    @OneToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "INVITATIONRESPONSE_ID")
    var response: InvitationResponse = InvitationResponse(emitter,group)*/

    constructor() : this(
        User(),
        User(),
        Group(),) {}
    constructor(id: Long, recipient: User, emitter: User, group: Group
    ): this(recipient, emitter, group){
        this.id = id
//        this.response = InvitationResponse(emitter,group)
    }
}