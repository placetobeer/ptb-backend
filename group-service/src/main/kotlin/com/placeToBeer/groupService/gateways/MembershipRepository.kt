package com.placeToBeer.groupService.gateways
import com.placeToBeer.groupService.entities.Membership
import org.springframework.data.repository.CrudRepository

interface MembershipRepository : CrudRepository<Membership, Long> {
}