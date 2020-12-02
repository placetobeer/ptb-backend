package com.placeToBeer.groupService.entities

import javax.persistence.*

@Entity
@Table(schema = "users") //schema = "users"
data class User
        constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id:Int,
        var name:String){
        constructor():this(0, "")
}