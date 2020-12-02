package com.placeToBeer.groupService.gateways
import com.placeToBeer.groupService.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long>{
}