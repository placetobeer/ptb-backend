package com.placeToBeer.groupService.entities

import javax.persistence.Entity

@Entity
public class Membership(
        val user:User,
        var role:Role) {
}