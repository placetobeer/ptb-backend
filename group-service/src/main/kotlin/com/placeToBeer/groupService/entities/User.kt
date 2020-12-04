package com.placeToBeer.groupService.entities

import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Table(name = "user") //schema = "users"
data class User
        constructor(
        @Id
        @NotNull
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id:Long,
        @Column(nullable = false)
        var name:String){
        constructor():this(0, "")
}