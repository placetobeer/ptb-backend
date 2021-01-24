package com.placeToBeer.groupService.entities

import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
data class User
        constructor(
        @Column(nullable = false)
        var name:String){

        @Id
        @Column(name="USER_ID")
        @NotNull
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id:Long? = null

        constructor():this("")
        constructor(id: Long, name: String): this(name){
                this.id = id
        }
}