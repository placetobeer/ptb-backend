package com.placeToBeer.groupService.entities

import io.swagger.annotations.ApiModelProperty
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Table(name = "friend_group")
data class Group(
        @NotNull
        @ApiModelProperty(
                value = "Name of the group",
                example = "Bratis Kartoffeln",
                required = true)
        var name: String) {

        @Id
        @Column(name="GROUP_ID")
        @NotNull
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @ApiModelProperty(
                value = "Autogenerated database id",
                example = "1",
                required = true)
        var id: Long? = null

        constructor() : this("")
        constructor(id: Long, name: String): this(name){
                this.id = id
        }

}