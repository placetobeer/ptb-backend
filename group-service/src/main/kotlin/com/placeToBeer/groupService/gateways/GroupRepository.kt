package com.placeToBeer.groupService.gateways
import com.placeToBeer.groupService.entities.Group
import org.springframework.data.repository.CrudRepository

interface GroupRepository : CrudRepository<Group,Long> {
}