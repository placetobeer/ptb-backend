package com.placeToBeer.groupService.gateways
import com.placeToBeer.groupService.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long>{
}