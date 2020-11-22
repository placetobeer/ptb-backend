package com.placeToBeer.groupService.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
class Group(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        var name: String,
        @OneToMany()
        var memberships: MutableList<Membership>) {
    protected constructor():this(0,"",mutableListOf(Membership())) {
    }

}