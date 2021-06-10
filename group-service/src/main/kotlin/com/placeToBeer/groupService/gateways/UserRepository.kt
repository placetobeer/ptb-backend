package com.placeToBeer.groupService.gateways
import com.placeToBeer.groupService.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long>{
    fun getUserByEmail(email: String): Optional<User>
}