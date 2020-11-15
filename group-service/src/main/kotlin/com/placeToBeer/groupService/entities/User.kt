package com.placeToBeer.groupService.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class User(
        @Id val id:Int,
        var name:String) {
    constructor() {

    }
}