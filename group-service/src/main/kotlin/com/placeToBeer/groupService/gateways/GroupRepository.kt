package com.placeToBeer.groupService.gateways
import com.placeToBeer.groupService.entities.Group
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : CrudRepository<Group,Long> {
}