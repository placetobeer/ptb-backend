package com.placeToBeer.groupService.gateways
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.User
import org.springframework.data.repository.CrudRepository

interface MembershipRepository : CrudRepository<Membership, Long> {
    fun findByMember(user:User):List<Membership>
}