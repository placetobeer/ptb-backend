package com.placeToBeer.groupService.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
class User
        constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id:Int,
        var name:String){
        constructor():this(0, "")
}